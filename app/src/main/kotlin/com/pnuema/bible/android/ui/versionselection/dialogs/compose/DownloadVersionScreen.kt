package com.pnuema.bible.android.ui.versionselection.dialogs.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pnuema.bible.android.R
import com.pnuema.bible.android.ui.BibleTheme
import com.pnuema.bible.android.ui.versionselection.dialogs.DownloadProgress

@Composable
fun DownloadVersionScreen(
    modifier: Modifier = Modifier,
    state: DownloadProgress,
) {
    BibleTheme {
        Column(
            modifier = modifier
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                text = stringResource(R.string.downloading_version_x, state.versionToDownloadDisplay)
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = 2.dp),
                progress = state.progress.toFloat() / state.max.toFloat(),
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.secondaryContainer
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DownloadVersionScreen_Preview() {
    BibleTheme {
        DownloadVersionScreen(
            state = DownloadProgress(
                versionToDownload = "KJV",
                versionToDownloadDisplay = "King James Version",
                max = 100,
                progress = 40,
                isComplete = false,
            )
        )
    }
}