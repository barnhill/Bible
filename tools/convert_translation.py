#!/usr/bin/env python3

# script for importing translations into mysql from https://github.com/scrollmapper/bible_databases/tree/master/sources
# python3 /Users/b359923/Desktop/convert_translation.py \
#    /Users/b359923/Desktop/akjv.json \
#    /Users/b359923/Desktop/akjv.csv \
#    6 \
#    --db-password 'DB_PASSWORD'

import argparse
import csv
import json
import re
import sys
from pathlib import Path
from typing import Any, Dict, List, Optional, Tuple

import mysql.connector


def to_int(x: Any) -> Optional[int]:
    try:
        return int(str(x).strip())
    except Exception:
        return None


def write_mysql_safe_csv(output_csv: Path, rows: List[Tuple[int, int, int, int, str]]) -> None:
    output_csv.parent.mkdir(parents=True, exist_ok=True)
    with output_csv.open("w", encoding="utf-8", newline="") as f:
        writer = csv.writer(
            f,
            delimiter=",",
            quotechar='"',
            quoting=csv.QUOTE_ALL,
            lineterminator="\n",
            doublequote=True,
        )
        writer.writerow(["version_id", "book", "chapter", "verse", "verse_text"])
        for version_id, book, chapter, verse, verse_text in rows:
            writer.writerow([
                str(version_id),
                str(book),
                str(chapter),
                str(verse),
                "" if verse_text is None else str(verse_text),
            ])


def assign_book_ids(rows: List[Tuple[str, int, int, str]], version_id: int) -> List[Tuple[int, int, int, int, str]]:
    book_to_id: Dict[str, int] = {}
    next_id = 1
    out: List[Tuple[int, int, int, int, str]] = []

    for book, chapter, verse, text in rows:
        book_key = book.strip()
        if book_key not in book_to_id:
            book_to_id[book_key] = next_id
            next_id += 1
        out.append((version_id, book_to_id[book_key], chapter, verse, text))
    return out


BOOK_KEYS = ["book", "book_name", "bookName", "bk", "b"]
CHAPTER_KEYS = ["chapter", "chapter_id", "chapterId", "chap", "c", "chapterNumber"]
VERSE_KEYS = ["verse", "verse_number", "verseNumber", "v", "verseNo"]
TEXT_KEYS = ["text", "verse_text", "verseText", "content", "t"]


def first_present(d: Dict[str, Any], keys: List[str]) -> Any:
    for k in keys:
        if k in d and d[k] is not None:
            return d[k]
    return None


def parse_object_records(payload: Any) -> List[Tuple[str, int, int, str]]:
    found: List[Tuple[str, int, int, str]] = []

    def walk(node: Any) -> None:
        if isinstance(node, dict):
            book = first_present(node, BOOK_KEYS)
            chapter = first_present(node, CHAPTER_KEYS)
            verse = first_present(node, VERSE_KEYS)
            text = first_present(node, TEXT_KEYS)

            if book is not None and chapter is not None and verse is not None and text is not None:
                c = to_int(chapter)
                v = to_int(verse)
                if c is not None and v is not None:
                    found.append((str(book), c, v, str(text)))

            for val in node.values():
                walk(val)
        elif isinstance(node, list):
            for item in node:
                walk(item)

    walk(payload)
    return found


REF_RE = re.compile(r"^\s*(.+?)\s+(\d+)\s*:\s*(\d+)\s*$")


def parse_reference_map(payload: Any) -> List[Tuple[str, int, int, str]]:
    if not isinstance(payload, dict):
        return []
    rows: List[Tuple[str, int, int, str]] = []
    for k, v in payload.items():
        if not isinstance(k, str):
            continue
        m = REF_RE.match(k)
        if not m:
            continue
        rows.append((m.group(1).strip(), int(m.group(2)), int(m.group(3)), "" if v is None else str(v)))
    return rows


def parse_nested_book_chapter_verse(payload: Any) -> List[Tuple[str, int, int, str]]:
    if not isinstance(payload, dict):
        return []

    rows: List[Tuple[str, int, int, str]] = []
    for book_name, chapters in payload.items():
        if not isinstance(chapters, dict):
            continue
        for ch_key, verses in chapters.items():
            chapter = to_int(ch_key)
            if chapter is None or not isinstance(verses, dict):
                continue
            for v_key, verse_text in verses.items():
                verse = to_int(v_key)
                if verse is None:
                    continue
                rows.append((str(book_name), chapter, verse, "" if verse_text is None else str(verse_text)))
    return rows


def parse_books_chapters_shape(payload: Any) -> List[Tuple[str, int, int, str]]:
    if not isinstance(payload, dict):
        return []

    books = payload.get("books")
    if not isinstance(books, list):
        return []

    rows: List[Tuple[str, int, int, str]] = []

    for book_obj in books:
        if not isinstance(book_obj, dict):
            continue

        book_name = (
            book_obj.get("name")
            or book_obj.get("book")
            or book_obj.get("title")
            or book_obj.get("abbreviation")
            or "Unknown"
        )
        chapters = book_obj.get("chapters")
        if not isinstance(chapters, list):
            continue

        for ch_index, ch_obj in enumerate(chapters, start=1):
            chapter_id = ch_index
            verse_nodes: Any = None

            if isinstance(ch_obj, dict):
                chapter_id = to_int(ch_obj.get("chapter")) or to_int(ch_obj.get("number")) or ch_index
                verse_nodes = ch_obj.get("verses") or ch_obj.get("content") or ch_obj.get("items")
            elif isinstance(ch_obj, list):
                verse_nodes = ch_obj
            else:
                continue

            if not isinstance(verse_nodes, list):
                continue

            for v_index, v_obj in enumerate(verse_nodes, start=1):
                if isinstance(v_obj, dict):
                    verse_num = (
                        to_int(v_obj.get("verse"))
                        or to_int(v_obj.get("number"))
                        or to_int(v_obj.get("v"))
                        or v_index
                    )
                    verse_text = (
                        v_obj.get("text")
                        or v_obj.get("content")
                        or v_obj.get("t")
                        or ""
                    )
                    rows.append((str(book_name), int(chapter_id), int(verse_num), str(verse_text)))
                elif isinstance(v_obj, str):
                    rows.append((str(book_name), int(chapter_id), v_index, v_obj))

    return rows


def dedupe_rows(rows: List[Tuple[str, int, int, str]]) -> List[Tuple[str, int, int, str]]:
    seen = set()
    out = []
    for r in rows:
        key = (r[0].strip(), r[1], r[2], r[3])
        if key not in seen:
            seen.add(key)
            out.append((r[0].strip(), r[1], r[2], r[3]))
    return out


def load_rows_to_mysql(
    rows: List[Tuple[int, int, int, int, str]],
    host: str,
    port: int,
    user: str,
    password: str,
    database: str,
    table: str = "import",
    batch_size: int = 2000,
) -> int:
    conn = mysql.connector.connect(
        host=host,
        port=port,
        user=user,
        password=password,
        database=database,
        autocommit=False,
    )

    insert_sql = f"""
    INSERT INTO `{table}` (version_id, book, chapter, verse, verse_text)
    VALUES (%s, %s, %s, %s, %s)
    """

    total = 0
    batch: List[Tuple[int, int, int, int, str]] = []

    with conn, conn.cursor() as cur:
        for row in rows:
            batch.append(row)
            if len(batch) >= batch_size:
                cur.executemany(insert_sql, batch)
                total += len(batch)
                batch.clear()

        if batch:
            cur.executemany(insert_sql, batch)
            total += len(batch)

        conn.commit()

    return total


def main() -> None:
    parser = argparse.ArgumentParser(description="Convert Bible JSON and load into MySQL import table.")
    parser.add_argument("input_json", help="Path to input JSON")
    parser.add_argument("output_csv", help="Path to output CSV")
    parser.add_argument("version_id", type=int, help="Version ID for each row")

    parser.add_argument("--db-host", default="192.168.1.5")
    parser.add_argument("--db-port", type=int, default=33061)
    parser.add_argument("--db-user", default="root")
    parser.add_argument("--db-password", required=True)
    parser.add_argument("--db-name", default="firefly_db")
    parser.add_argument("--db-table", default="import")
    parser.add_argument("--sample", type=int, default=5)

    args = parser.parse_args()

    input_path = Path(args.input_json)
    output_path = Path(args.output_csv)

    if not input_path.exists():
        print(f"Input not found: {input_path}", file=sys.stderr)
        sys.exit(1)

    with input_path.open("r", encoding="utf-8-sig") as f:
        payload = json.load(f)

    candidates: List[Tuple[str, List[Tuple[str, int, int, str]]]] = [
        ("books/chapters shape", parse_books_chapters_shape(payload)),
        ("object records", parse_object_records(payload)),
        ("reference-key map", parse_reference_map(payload)),
        ("nested book->chapter->verse map", parse_nested_book_chapter_verse(payload)),
    ]

    parser_name, parsed_rows = max(candidates, key=lambda x: len(x[1]))
    parsed_rows = dedupe_rows(parsed_rows)

    if not parsed_rows:
        raise ValueError(
            "Could not parse verse rows from this JSON. "
            "Please paste the first 40-80 lines so mapping can be made exact."
        )

    final_rows = assign_book_ids(parsed_rows, args.version_id)

    write_mysql_safe_csv(output_path, final_rows)
    print(f"Parser used: {parser_name}")
    print(f"Wrote {len(final_rows)} rows to {output_path}")
    for row in final_rows[:max(0, args.sample)]:
        print("sample:", row)

    inserted = load_rows_to_mysql(
        rows=final_rows,
        host=args.db_host,
        port=args.db_port,
        user=args.db_user,
        password=args.db_password,
        database=args.db_name,
        table=args.db_table,
    )
    print(f"Inserted rows into {args.db_name}.{args.db_table}: {inserted}")


if __name__ == "__main__":
    main()