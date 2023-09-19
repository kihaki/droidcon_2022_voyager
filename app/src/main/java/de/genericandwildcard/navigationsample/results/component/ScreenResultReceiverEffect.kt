package de.genericandwildcard.navigationsample.results.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import cafe.adriel.voyager.core.screen.ScreenKey
import de.genericandwildcard.navigationsample.results.ScreenResult
import de.genericandwildcard.navigationsample.results.screenResults
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
inline fun <reified T : ScreenResult> TriggerOnScreenResultEffect(
    key: ScreenKey,
    noinline onResult: (T) -> Unit,
) {
    val resultReceiver by rememberUpdatedState(onResult)

    LaunchedEffect(key1 = key) {
        screenResults
            .map { it[key] }
            .distinctUntilChanged()
            .collectLatest { result ->
                if (result != null) {
                    resultReceiver.invoke(result as T)
                }
            }
    }
}
