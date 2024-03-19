package com.pnuema.bible.android.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment

fun Fragment.setContent(
    compositionStrategy: ViewCompositionStrategy = ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed,
    content: @Composable () -> Unit
): ComposeView = ComposeView(requireContext()).apply {
    setViewCompositionStrategy(compositionStrategy)
    setContent(content)
}