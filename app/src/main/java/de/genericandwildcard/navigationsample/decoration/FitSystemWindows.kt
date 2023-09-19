package de.genericandwildcard.navigationsample.decoration

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.core.view.ViewCompat

val LocalParentHasSystemBarPadding = staticCompositionLocalOf { false }
val LocalParentHasNavigationBarPadding = staticCompositionLocalOf { false }

data class FitSystemWindows(
    val fitSystemBar: Boolean = true,
    val fitNavigationBar: Boolean = true,
) : CustomScreenDecoration {

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Decoration(content: @Composable () -> Unit) {
        // If the parent already fits the system bars do not double that space
        val parentHasSystemBarPadding = LocalParentHasSystemBarPadding.current
        val addSystemBarPadding by remember {
            derivedStateOf {
                if (parentHasSystemBarPadding) false else fitSystemBar
            }
        }

        // If the parent already fits the navigation bars, do not double that space
        val parentHasNavigationBarPadding = LocalParentHasNavigationBarPadding.current
        val addNavigationBarPadding by remember {
            derivedStateOf {
                if (parentHasNavigationBarPadding) false else fitNavigationBar
            }
        }

        Column(
            modifier = Modifier.imePadding(),
        ) {
            AnimatedVisibility(visible = addSystemBarPadding) {
                SystemBarSpacer()
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                CompositionLocalProvider(
                    LocalParentHasSystemBarPadding provides (addSystemBarPadding || parentHasSystemBarPadding),
                    LocalParentHasNavigationBarPadding provides (addNavigationBarPadding || parentHasNavigationBarPadding)
                ) {
                    content()
                }
            }
            AnimatedVisibility(visible = addNavigationBarPadding && !WindowInsets.isImeVisible) {
                NavigationBarSpacer()
            }
        }
    }
}

@Composable
private fun NavigationBarSpacer() {
    val bottomNavigationBarHeight by bottomNavigationBarHeight()
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(bottomNavigationBarHeight),
        color = Color.Black,
        content = {},
    )
}


@Composable
fun bottomNavigationBarHeight(): State<Dp> {
    val navigationBars = WindowInsets.navigationBars
    val density = LocalDensity.current
    return remember {
        derivedStateOf {
            with(density) { navigationBars.getBottom(this).toDp() }
        }
    }
}

@Composable
private fun SystemBarSpacer() {
    val systemBars = WindowInsets.systemBars
    val density = LocalDensity.current
    val topSystemBarHeight by remember {
        derivedStateOf {
            with(density) { systemBars.getTop(this).toDp() }
        }
    }

    val isDark = isSystemInDarkTheme()
    val view = LocalView.current
    SideEffect {
        ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = isDark
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(topSystemBarHeight),
        color = MaterialTheme.colorScheme.primary,
        content = {},
    )
}
