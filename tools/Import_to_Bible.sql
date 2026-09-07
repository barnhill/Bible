START TRANSACTION;

INSERT INTO bible (version_id, book, chapter, verse, verse_text)
SELECT i.version_id, i.book, i.chapter, i.verse, i.verse_text
FROM import i
WHERE NOT EXISTS (
    SELECT 1
    FROM bible b
    WHERE b.version_id = i.version_id
      AND b.book = i.book
      AND b.chapter = i.chapter
      AND b.verse = i.verse
);

-- how many rows this insert added in this session
SELECT ROW_COUNT() AS inserted_rows;

-- optional sanity checks before finalizing
SELECT COUNT(*) AS import_rows FROM import;
SELECT COUNT(*) AS bible_rows  FROM bible where version_id = 6;

-- COMMIT;
-- ROLLBACK; -- instead of COMMIT if results are not what you expect