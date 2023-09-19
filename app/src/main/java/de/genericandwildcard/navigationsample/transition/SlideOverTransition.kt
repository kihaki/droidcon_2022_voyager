package de.genericandwildcard.navigationsample.transition

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.StackEvent.Pop
import cafe.adriel.voyager.navigator.Navigator

object SlideOverTransition : CustomScreenTransition {
    override fun screenTransition(
        scope: AnimatedContentTransitionScope<Screen>,
        navigator: Navigator,
    ): ContentTransform =
        when (navigator.lastEvent) {
            Pop -> slideInHorizontally { -it / 2 } togetherWith slideOutHorizontally { it }
            else -> slideInHorizontally { it } togetherWith slideOutHorizontally { -it / 2 }
        }
}
