package de.genericandwildcard.navigationsample.transition

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator

interface CustomScreenTransition {
    fun screenTransition(
        scope: AnimatedContentTransitionScope<Screen>,
        navigator: Navigator,
    ): ContentTransform
}
