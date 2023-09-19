package de.genericandwildcard.navigationsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import de.genericandwildcard.navigationsample.components.BottomButtonsScaffold
import de.genericandwildcard.navigationsample.screens.currentEmojiIndex
import de.genericandwildcard.navigationsample.ui.theme.NavigationSampleTheme
import de.genericandwildcard.navigationsample.utils.Emoji
import de.genericandwildcard.navigationsample.utils.EmojiBox
import de.genericandwildcard.navigationsample.utils.SoftColors

class SimpleNavigatorActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationSampleTheme {
                Navigator(
                    screen = SimpleEmojiScreen(
                        emoji = Emoji.STRONG,
                        color = SoftColors[1],
                    ),
                ) { navigator ->
                    SlideTransition(navigator = navigator)
                }
            }
        }
    }
}

data class SimpleEmojiScreen(
    val color: Color,
    val emoji: Emoji,
) : Screen {

    override val key: ScreenKey get() = toString()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        BottomButtonsScaffold(
            onNext = { navigator?.push(SimpleEmojiScreen()) }
        ) {
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
}

@Suppress("FunctionName")
fun SimpleEmojiScreen(
    emojiIndex: Int = currentEmojiIndex.getAndIncrement(),
): Screen = SimpleEmojiScreen(
    color = SoftColors[emojiIndex],
    emoji = Emoji.values().let { it[emojiIndex % it.size] },
)

@Preview
@Composable
fun SimpleEmojiScreenPreview() {
    NavigationSampleTheme {
        SimpleEmojiScreen(
            emoji = Emoji.STRONG,
            color = SoftColors[1],
        ).Content()
    }
}