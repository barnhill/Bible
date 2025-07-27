package com.pnuema.bible.android.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.pnuema.bible.android.ui.BibleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBar(
    modifier: Modifier = Modifier,
    title: String,
    backArrowIcon: Boolean = true,
    onBackPressed: () -> Unit,
) {
    Row(
        modifier = modifier
            .background(Color.Transparent)
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 8.dp)
    ) {
        TopAppBar(
            modifier = Modifier
                .padding(0.dp)
                .wrapContentHeight(),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            title = {
                Text(
                    text = title,
                    fontSize = TextUnit(18f, TextUnitType.Sp)
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { onBackPressed() }) {
                    Icon(
                        imageVector = if (backArrowIcon) Icons.AutoMirrored.Filled.ArrowBack
                                      else Icons.Filled.Close,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BibleTopbar_Preview() {
    BibleTheme {
        BackTopBar(
            title = "Test Title Here",
            onBackPressed = {}
        )
    }
}