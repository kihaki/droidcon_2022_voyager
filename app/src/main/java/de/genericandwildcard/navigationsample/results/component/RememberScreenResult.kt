package de.genericandwildcard.navigationsample.results.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.ScreenKey
import de.genericandwildcard.navigationsample.results.ScreenResult

@Composable
inline fun <reified T : ScreenResult> rememberScreenResult(
    resultSourceKey: ScreenKey,
): State<T?> {
    val screenResult = remember { mutableStateOf<T?>(null) }

    TriggerOnScreenResultEffect<T>(
        key = resultSourceKey,
    ) { resultFromNextScreen ->
        screenResult.value = resultFromNextScreen
    }

    return screenResult
}
