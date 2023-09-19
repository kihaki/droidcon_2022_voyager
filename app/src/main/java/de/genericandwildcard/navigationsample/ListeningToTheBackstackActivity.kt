package de.genericandwildcard.navigationsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import de.genericandwildcard.navigationsample.components.BottomButtonsScaffold
import de.genericandwildcard.navigationsample.components.SlidingTitle
import de.genericandwildcard.navigationsample.transition.component.ScreenBasedTransition
import de.genericandwildcard.navigationsample.ui.theme.NavigationSampleTheme
import de.genericandwildcard.navigationsample.utils.Emoji
import de.genericandwildcard.navigationsample.utils.SoftColors

class ListeningToTheBackstackActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationSampleTheme {
                Navigator(
                    screen = SmartBottomBarWrapperWithTitleScreen(
                        initialScreen = NestedEmojiScreen(
                            emoji = Emoji.STRONG,
                            color = SoftColors[1],
                        )
                    ),
                ) {
                    SlideTransition(navigator = it)
                }
            }
        }
    }
}

data class SmartBottomBarWrapperWithTitleScreen(
    val initialScreen: Screen,
) : Screen {

    override val key: ScreenKey get() = toString()

    @Composable
    override fun Content() {
        Navigator(
            screen = initialScreen
        ) { navigator ->
            BottomButtonsScaffold(
                onNext = { navigator.push(NestedEmojiScreen()) },
                onPrevious = navigator.takeIf { it.canPop }?.let {
                    { it.pop() }
                }
            ) {
                Column {
                    val currentTitle by remember {
                        derivedStateOf {
                            (navigator.lastItem as? HasTitle)?.title
                                ?: "Not a Screen with a Title"
                        }
                    }
                    SlidingTitle(title = currentTitle)
                    ScreenBasedTransition(navigator = navigator)
                }
            }
        }
    }
}