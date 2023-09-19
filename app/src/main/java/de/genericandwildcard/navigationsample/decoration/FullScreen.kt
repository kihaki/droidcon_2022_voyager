package de.genericandwildcard.navigationsample.decoration

import androidx.compose.runtime.Composable

object FullScreen : CustomScreenDecoration {
    @Composable
    override fun Decoration(content: @Composable () -> Unit) = content()
}
