package com.pnuema.bible.android.ui.versionselection.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
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
    onDownloadClicked: (String) -> Unit,
) {
    BibleTheme {
        Row(
            modifier = modifier
                .padding(vertical = 16.dp),
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .align(alignment = Alignment.CenterVertically)
                    .clickable { onVersionClicked(version.abbreviation) },
                text = version.getDisplayText(),
                color = if (isCurrentSelectedVersion) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground
            )
            Image(
                modifier = Modifier
                    .clickable { onDownloadClicked(version.abbreviation) }
                    .align(alignment = Alignment.CenterVertically),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                imageVector = ImageVector.vectorResource(R.drawable.cloud_download_outline),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VersionItem_Preview() {
    BibleTheme {
        VersionItem(
            version = object : IVersion{
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
            isCurrentSelectedVersion = true,
            onVersionClicked = {},
            onDownloadClicked = {}
        )
    }
}