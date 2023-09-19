package de.genericandwildcard.navigationsample.transition.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.stack.StackEvent.Idle
import cafe.adriel.voyager.core.stack.StackEvent.Pop
import cafe.adriel.voyager.core.stack.StackEvent.Push
import cafe.adriel.voyager.core.stack.StackEvent.Replace
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScreenTransition
import cafe.adriel.voyager.transitions.ScreenTransitionContent
import de.genericandwildcard.navigationsample.decoration.CustomScreenDecoration
import de.genericandwildcard.navigationsample.decoration.FitSystemWindows
import de.genericandwildcard.navigationsample.transition.CustomScreenTransition
import de.genericandwildcard.navigationsample.transition.SlideOverTransition

@Composable
fun ScreenBasedTransition(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    defaultTransition: CustomScreenTransition = SlideOverTransition,
    defaultScreenDecoration: CustomScreenDecoration = remember { FitSystemWindows() },
    content: ScreenTransitionContent = { currentScreen ->
        ((currentScreen as? CustomScreenDecoration) ?: defaultScreenDecoration).Decoration {
            currentScreen.Content()
        }
    }
) {
    ScreenTransition(
        navigator = navigator,
        modifier = modifier,
        content = content,
        transition = {
            // Find out which screen defines the transition
            val transitionSource = when (navigator.lastEvent) {
                Pop, Replace -> initialState
                Idle, Push -> targetState
            }

            // Get the transition from the screen, if it is providing any
            val screenTransition = (transitionSource as? CustomScreenTransition)
                ?.screenTransition(this, navigator)
                ?: defaultTransition.screenTransition(this, navigator)

            // Set the zIndex for the transition:
            // -> screens higher up on the stack must rendered on top of screens below
            // during transitions, this is important.
            // We use the index of the item to determine the zIndex in the UI
            val stackSize = navigator.items.size
            screenTransition.targetContentZIndex = when (navigator.lastEvent) {
                // Make sure that content that's popped is rendered on top
                Pop, Replace -> (stackSize - 1)
                // Make sure that content that's pushed is rendered on top
                Idle, Push -> stackSize
            }.toFloat()

            // Return the transition
            screenTransition
        }
    )
}