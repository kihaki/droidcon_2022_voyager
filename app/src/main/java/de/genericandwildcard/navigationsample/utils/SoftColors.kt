package de.genericandwildcard.navigationsample.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import kotlin.random.Random.Default.nextInt

internal object SoftColors {
    private val colors = listOf(
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.Cyan,
        Color.Magenta,
    )

    fun random() = get(nextInt(colors.size))

    operator fun get(index: Int) = lerp(colors[index % colors.size], Color.White, .75f)
}

@Composable
internal fun rememberRandomSoftColor(strength: Float = .75f) =
    rememberSaveable(saver = colorSaver()) {
        lerp(SoftColors.random(), Color.White, strength)
    }