package de.genericandwildcard.navigationsample.results

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.ScreenKey
import de.genericandwildcard.navigationsample.utils.LocalActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ScreenResultsStore(
    screenResultsStoreOwner: ViewModelStoreOwner = LocalActivity,
) {
    @Suppress("UNUSED_VARIABLE")
    val screenResultsViewModel: ScreenResultsViewModel = viewModel(
        viewModelStoreOwner = screenResultsStoreOwner,
    )

    LaunchedEffect(screenResultsViewModel) {
        ScreenResultsStoreProxy.screenResultsModel = screenResultsViewModel
    }
}

/**
 * Needs to be implemented on top level to provide screen results with state saving
 */
class ScreenResultsViewModel(
    private val state: SavedStateHandle,
) : ViewModel() {

    val screenResults = MutableStateFlow(
        state.get<Map<ScreenKey, ScreenResult>>(SCREEN_RESULTS_SAVEDSTATE_KEY) ?: emptyMap()
    )

    fun <T : ScreenResult> setResult(screenResultSourceKey: ScreenKey, result: T) {
        screenResults.value = screenResults.value.toMutableMap() + (screenResultSourceKey to result)
    }

    fun removeResult(screenResultSourceKey: ScreenKey) {
        screenResults.value = screenResults.value.toMutableMap() - screenResultSourceKey
    }

    override fun onCleared() {
        state[SCREEN_RESULTS_SAVEDSTATE_KEY] = screenResults.value
        super.onCleared()
    }
}

// This is private!
private object ScreenResultsStoreProxy {
    lateinit var screenResultsModel: ScreenResultsViewModel

    fun <T : ScreenResult> setResult(screenResultSourceKey: ScreenKey, result: T) =
        screenResultsModel.setResult(screenResultSourceKey = screenResultSourceKey, result = result)

    fun removeResult(screenResultSourceKey: ScreenKey) =
        screenResultsModel.removeResult(screenResultSourceKey)
}

// These are public, only screens with the matching result type can modify the results
fun <T : ScreenResult> ScreenWithResult<T>.setScreenResult(
    result: T,
    screenResultSourceKey: ScreenKey = key,
) = ScreenResultsStoreProxy.setResult(
    screenResultSourceKey = screenResultSourceKey,
    result = result,
)

fun <T : ScreenResult> ScreenWithResult<T>.clearScreenResult() =
    ScreenResultsStoreProxy.removeResult(key)

// Anyone can observe the results though
val screenResults: StateFlow<Map<ScreenKey, ScreenResult>>
    get() = ScreenResultsStoreProxy.screenResultsModel.screenResults

private const val SCREEN_RESULTS_SAVEDSTATE_KEY = "screen_results"
