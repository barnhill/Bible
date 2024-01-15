package com.pnuema.bible.android.ui.versionselection.ui.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.database.VersionOffline
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.BibleTheme

@Composable
fun VersionSelectionScreen(
    versions: List<IVersion>,
    onVersionClicked: (String) -> Unit,
    onDownloadClicked: (String) -> Unit,
) {
    BibleTheme {
        LazyColumn {
           items(versions) {
                VersionItem(
                    version = it,
                    isCurrentSelectedVersion = CurrentSelected.version == it.abbreviation,
                    onVersionClicked = onVersionClicked,
                    onDownloadClicked = onDownloadClicked,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VersionSelectionScreen_Preview() {
    BibleTheme {
        VersionSelectionScreen(
            versions = listOf(
                object : IVersion {
                    override val id: Int
                        get() = 1
                    override val language: String
                        get() = ""
                    override val abbreviation: String
                        get() = ""
                    override val copyright: String
                        get() = ""

                    override fun getDisplayText(): String = "King James Version"

                    override fun convertToOfflineModel(): VersionOffline {
                        return VersionOffline(
                            abbreviation = "KJV",
                            id = 1,
                            version = "KJV",
                            url = "",
                            publisher = "",
                            copyright = "",
                            copyrightInfo = "",
                            language = "",
                            completeOffline = false
                        )
                    }
                },
                object : IVersion {
                    override val id: Int
                        get() = 2
                    override val language: String
                        get() = ""
                    override val abbreviation: String
                        get() = ""
                    override val copyright: String
                        get() = ""

                    override fun getDisplayText(): String = "American Standard Version"

                    override fun convertToOfflineModel(): VersionOffline {
                        return VersionOffline(
                            abbreviation = "ASV",
                            id = 2,
                            version = "ASV",
                            url = "",
                            publisher = "",
                            copyright = "",
                            copyrightInfo = "",
                            language = "",
                            completeOffline = false
                        )
                    }
                },
                object : IVersion {
                    override val id: Int
                        get() = 3
                    override val language: String
                        get() = ""
                    override val abbreviation: String
                        get() = ""
                    override val copyright: String
                        get() = ""

                    override fun getDisplayText(): String = "Easy Read Version"

                    override fun convertToOfflineModel(): VersionOffline {
                        return VersionOffline(
                            abbreviation = "ERV",
                            id = 3,
                            version = "ERV",
                            url = "",
                            publisher = "",
                            copyright = "",
                            copyrightInfo = "",
                            language = "",
                            completeOffline = false
                        )
                    }
                }
            ),
            onVersionClicked = {},
            onDownloadClicked = {}
        )
    }
}