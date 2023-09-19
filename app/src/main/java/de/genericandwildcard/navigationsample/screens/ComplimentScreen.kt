package de.genericandwildcard.navigationsample.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import de.genericandwildcard.navigationsample.decoration.CustomScreenDecoration
import de.genericandwildcard.navigationsample.decoration.FullScreen
import de.genericandwildcard.navigationsample.transition.CustomScreenTransition
import de.genericandwildcard.navigationsample.transition.SlideUpTransition

data class ComplimentScreen(
    val message: String,
) : Screen,
    CustomScreenDecoration by FullScreen,
    CustomScreenTransition by SlideUpTransition {

    override val key: ScreenKey get() = toString()

    @Composable
    override fun Content() {
        SetStatusBarStatusForComposableEffect(isLight = true)

        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.displayLarge,
                )
            }
        }
    }
}

@Composable
fun SetStatusBarStatusForComposableEffect(
    isLight: Boolean,
) {
    val view = LocalView.current
    val previousStatusBarIconColor =
        remember { ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars }
    DisposableEffect(Unit) {
        ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = isLight
        onDispose {
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars =
                previousStatusBarIconColor ?: false
        }
    }
}