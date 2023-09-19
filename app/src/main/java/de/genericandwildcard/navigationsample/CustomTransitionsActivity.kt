package de.genericandwildcard.navigationsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.genericandwildcard.navigationsample.components.BottomButtonsScaffold
import de.genericandwildcard.navigationsample.components.SlidingTitle
import de.genericandwildcard.navigationsample.screens.currentEmojiIndex
import de.genericandwildcard.navigationsample.transition.CustomScreenTransition
import de.genericandwildcard.navigationsample.transition.SlideUpTransition
import de.genericandwildcard.navigationsample.transition.component.ScreenBasedTransition
import de.genericandwildcard.navigationsample.ui.theme.NavigationSampleTheme
import de.genericandwildcard.navigationsample.utils.Emoji
import de.genericandwildcard.navigationsample.utils.EmojiBox
import de.genericandwildcard.navigationsample.utils.SoftColors

class CustomTransitionsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationSampleTheme {
                Navigator(
                    screen = CustomTransitionsBottomBarWrapperScreen(
                        initialScreen = ScreenWithButton,
                    ),
                ) {
                    ScreenBasedTransition(navigator = it)
                }
            }
        }
    }
}

object ScreenWithButton : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            ElevatedButton(onClick = { navigator.push(ModalEmojiScreen()) }) {
                Text("Click Me!")
            }
        }
    }
}

data class ModalEmojiScreen(
    val color: Color,
    val emoji: Emoji,
) : Screen,
    HasTitle,
    CustomScreenTransition by SlideUpTransition {

    override val key: ScreenKey get() = toString()

    override val title: String get() = emoji.toString()

    @Composable
    override fun Content() {
        LazyColumn {
            items(5) {
                EmojiBox(
                    modifier = Modifier
                        .fillParentMaxHeight(1f / 1.5f) // Show exactly 1.5 screens so we know we can scroll
                        .fillParentMaxWidth(),
                    background = color,
                    emoji = emoji,
                )
            }
        }
    }
}

@Suppress("FunctionName")
fun ModalEmojiScreen(
    emojiIndex: Int = currentEmojiIndex.getAndIncrement(),
): Screen = ModalEmojiScreen(
    color = SoftColors[emojiIndex],
    emoji = Emoji.values().let { it[emojiIndex % it.size] },
)

data class CustomTransitionsBottomBarWrapperScreen(
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