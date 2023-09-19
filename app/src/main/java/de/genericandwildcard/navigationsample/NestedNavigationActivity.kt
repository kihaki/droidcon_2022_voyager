package de.genericandwildcard.navigationsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import de.genericandwildcard.navigationsample.components.BottomButtonsScaffold
import de.genericandwildcard.navigationsample.screens.currentEmojiIndex
import de.genericandwildcard.navigationsample.ui.theme.NavigationSampleTheme
import de.genericandwildcard.navigationsample.utils.Emoji
import de.genericandwildcard.navigationsample.utils.EmojiBox
import de.genericandwildcard.navigationsample.utils.SoftColors

class NestedNavigationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationSampleTheme {
                Navigator(
                    screen = BottomBarWrapperScreen(
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

data class BottomBarWrapperScreen(
    val initialScreen: Screen,
) : Screen {

    override val key: ScreenKey get() = toString()

    @Composable
    override fun Content() {
        var innerNavigator by remember { mutableStateOf<Navigator?>(null) }
        BottomButtonsScaffold(
            onNext = { innerNavigator?.push(NestedEmojiScreen()) },
        ) {
            Navigator(
                screen = initialScreen
            ) {
                innerNavigator = it
                SlideTransition(navigator = it)
            }
        }
    }
}

interface HasTitle {
    val title: String
}

@Suppress("FunctionName")
fun NestedEmojiScreen(
    emojiIndex: Int = currentEmojiIndex.getAndIncrement(),
): Screen = NestedEmojiScreen(
    color = SoftColors[emojiIndex],
    emoji = Emoji.values().let { it[emojiIndex % it.size] },
)

data class NestedEmojiScreen(
    val color: Color,
    val emoji: Emoji,
) : Screen,
    HasTitle {

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