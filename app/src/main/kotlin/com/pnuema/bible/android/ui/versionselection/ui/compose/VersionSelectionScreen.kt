package com.pnuema.bible.android.ui.versionselection.ui.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.data.firefly.VersionsDomain
import com.pnuema.bible.android.database.VersionOffline
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.BibleTheme
import com.pnuema.bible.android.ui.versionselection.state.VersionsState

@Composable
fun VersionSelectionScreen(
    state: VersionsState,
    onVersionClicked: (String) -> Unit,
    onActionClicked: (IVersion) -> Unit,
    onDownloadApproved: (IVersion) -> Unit,
    onRemoveApproved: (IVersion) -> Unit,
    onDialogDismiss: () -> Unit,
) {
    BibleTheme {
        LazyColumn {
           items(state.versions.versions) {
                VersionItem(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    version = it,
                    isCurrentSelectedVersion = CurrentSelected.version == it.abbreviation,
                    onVersionClicked = onVersionClicked,
                    onActionClicked = onActionClicked,
                )
            }
        }

        state.downloadDialogVersion?.let {
            AlertDialog(
                onDismissRequest = onDialogDismiss,
                title = {
                    Text(text = stringResource(id = R.string.download_confirm_title))
                },
                text = {
                    Text(text = stringResource(id = R.string.download_confirm_message, it.getDisplayText()))
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onDialogDismiss()
                            onDownloadApproved(state.downloadDialogVersion)
                        }
                    ) {
                        Text(text = stringResource(id = R.string.download_confirm_yes))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = onDialogDismiss
                    ) {
                        Text(text = stringResource(id = R.string.download_confirm_no))
                    }
                },
            )
        }

        state.removeDialogVersion?.let {
            AlertDialog(
                onDismissRequest = onDialogDismiss,
                title = {
                    Text(text = stringResource(id = R.string.remove_confirm_title))
                },
                text = {
                    Text(text = stringResource(id = R.string.remove_confirm_message, it.getDisplayText()))
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onDialogDismiss()
                            onRemoveApproved(state.removeDialogVersion)
                        }
                    ) {
                        Text(text = stringResource(id = R.string.download_confirm_yes))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = onDialogDismiss
                    ) {
                        Text(text = stringResource(id = R.string.download_confirm_no))
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VersionSelectionScreen_Preview() {
    BibleTheme {
        VersionSelectionScreen(
            state = VersionsState(
                versions = VersionsDomain(
                    listOf(
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
                                    completeOffline = true
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
                    )
                )
            ),
            onVersionClicked = {},
            onActionClicked = {},
            onDialogDismiss = {},
            onDownloadApproved = {},
            onRemoveApproved = {},
        )
    }
}