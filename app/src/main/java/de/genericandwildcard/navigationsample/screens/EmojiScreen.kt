package de.genericandwildcard.navigationsample.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.concurrent.AtomicInt32
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.genericandwildcard.navigationsample.components.BottomButtonsScaffold
import de.genericandwildcard.navigationsample.decoration.CustomScreenDecoration
import de.genericandwildcard.navigationsample.decoration.FitSystemWindows
import de.genericandwildcard.navigationsample.utils.Emoji
import de.genericandwildcard.navigationsample.utils.EmojiBox
import de.genericandwildcard.navigationsample.utils.SoftColors

val currentEmojiIndex = AtomicInt32(0)

@Suppress("FunctionName")
fun EmojiScreen(
    emojiIndex: Int = currentEmojiIndex.getAndIncrement(),
    shoutOutName: String? = null,
): Screen = EmojiScreen(
    color = SoftColors[emojiIndex],
    emoji = Emoji.values().let { it[emojiIndex % it.size] },
    shoutOutName = shoutOutName,
)

data class EmojiScreen(
    val color: Color,
    val emoji: Emoji,
    val shoutOutName: String?,
) : Screen,
    CustomScreenDecoration by FitSystemWindows() {

    override val key: ScreenKey get() = toString()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        BottomButtonsScaffold(onNext = {
            navigator.push(EmojiScreen(shoutOutName = shoutOutName))
        }) {
            Box(contentAlignment = Alignment.BottomCenter) {
                LazyColumn {
                    items(5) {
                        EmojiBox(
                            modifier = Modifier
                                .fillParentMaxHeight(1f / 1.5f)
                                .fillParentMaxWidth(),
                            background = color,
                            emoji = emoji,
                        )
                    }
                }
                AnimatedVisibility(
                    modifier = Modifier.padding(all = 16.dp),
                    visible = shoutOutName != null
                ) {
                    ElevatedButton(onClick = { navigator.push(ComplimentScreen("Hi $shoutOutName!")) }) {
                        Text("Click me")
                    }
                }
            }
        }
    }
}