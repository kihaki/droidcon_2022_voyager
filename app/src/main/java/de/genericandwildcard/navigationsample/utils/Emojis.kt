package de.genericandwildcard.navigationsample.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import kotlin.random.Random.Default.nextInt

enum class Emoji(
    val emoji: String,
) {
    SMILE("\uD83D\uDE00"),
    CATS("\uD83D\uDE3A"),
    BOMBS("\uD83D\uDCA3"),
    MONKEY("\uD83D\uDE48"),
    SUNGLASSES("\uD83D\uDE0E"),
    POOP("\uD83D\uDCA9"),
    ITALIAN("\uD83E\uDD0C"),
    WAVE("\uD83D\uDC4B"),
    EXPLODING_HEAD("\uD83E\uDD2F"),
    STRONG("\uD83D\uDCAA");

    companion object {
        fun random() = values().let {
            it[nextInt(it.size)]
        }
    }
}

@Composable
internal fun rememberEmoji(index: Int) = rememberSaveable { Emoji.values()[index] }

@Composable
internal fun rememberRandomEmoji() = rememberSaveable { Emoji.random() }
