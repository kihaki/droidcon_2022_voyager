package de.genericandwildcard.navigationsample.decoration

import androidx.compose.runtime.Composable

interface CustomScreenDecoration {
    @Composable
    fun Decoration(content: @Composable () -> Unit)
}
