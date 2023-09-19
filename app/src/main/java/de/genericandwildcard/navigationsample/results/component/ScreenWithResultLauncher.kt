package de.genericandwildcard.navigationsample.results.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.genericandwildcard.navigationsample.results.ScreenResult
import de.genericandwildcard.navigationsample.results.ScreenWithResult
import de.genericandwildcard.navigationsample.results.clearScreenResult

/**
 * Typesafe way of launching screens and receiving the results in a lambda
 */
class ScreenWithResultLauncher<S, R : ScreenResult>(
    val screen: S,
    val navigator: Navigator,
) where S : Screen, S : ScreenWithResult<R> {

    fun launch() {
        screen.clearScreenResult()
        navigator.push(screen)
    }
}

/**
 * Typesafe way of launching screens and receiving the results in a lambda
 */
@Composable
inline fun <S, reified R : ScreenResult> rememberScreenWithResultLauncher(
    screen: S,
    navigator: Navigator = LocalNavigator.currentOrThrow,
    noinline onResult: (R) -> Unit = {},
): ScreenWithResultLauncher<S, R> where S : Screen, S : ScreenWithResult<R> {

    val screenResultLauncher = remember(screen, navigator) {
        ScreenWithResultLauncher(
            screen = screen,
            navigator = navigator,
        )
    }

    TriggerOnScreenResultEffect(
        key = screenResultLauncher.screen.key,
        onResult = onResult,
    )

    return screenResultLauncher
}
