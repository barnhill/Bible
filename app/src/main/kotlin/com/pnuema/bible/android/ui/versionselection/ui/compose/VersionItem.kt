package com.pnuema.bible.android.ui.versionselection.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.database.VersionOffline
import com.pnuema.bible.android.ui.BibleTheme

@Composable
fun VersionItem(
    modifier: Modifier = Modifier,
    version: IVersion,
    isCurrentSelectedVersion: Boolean,
    onVersionClicked: (String) -> Unit,
    onActionClicked: (IVersion) -> Unit,
) {
    BibleTheme {
        Box(
            modifier = modifier
                .clickable { onVersionClicked(version.abbreviation) },
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .weight(1f)
                        .align(alignment = Alignment.CenterVertically),
                    text = version.getDisplayText(),
                    color = if (isCurrentSelectedVersion) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground
                )

                Box(modifier = Modifier
                    .clickable { onActionClicked(version) }
                    .align(alignment = Alignment.CenterVertically),
                ) {
                    val downloadComplete = version.convertToOfflineModel().completeOffline
                    Image(
                        modifier = Modifier
                            .padding(all = 16.dp),
                        colorFilter = ColorFilter.tint(
                            if (downloadComplete)
                                MaterialTheme.colorScheme.error
                            else
                                MaterialTheme.colorScheme.secondary
                        ),
                        imageVector = ImageVector.vectorResource(
                            if (downloadComplete)
                                R.drawable.trash_can_outline
                            else
                                R.drawable.cloud_download_outline
                        ),
                        contentDescription = stringResource(
                            id = if (downloadComplete) {
                                R.string.download_icon_content_description
                            } else {
                                R.string.delete_icon_content_description
                            }
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun VersionItem_Preview() {
    BibleTheme {
        VersionItem(
            version = object : IVersion{
                override val id: Int
                    get() = 1
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
                        completeOffline = false
                    )
                }
            },
            isCurrentSelectedVersion = true,
            onVersionClicked = {},
            onActionClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun VersionItem_Offline_Preview() {
    BibleTheme {
        VersionItem(
            version = object : IVersion{
                override val id: Int
                    get() = 1
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
                        completeOffline = true
                    )
                }
            },
            isCurrentSelectedVersion = true,
            onVersionClicked = {},
            onActionClicked = {}
        )
    }
}