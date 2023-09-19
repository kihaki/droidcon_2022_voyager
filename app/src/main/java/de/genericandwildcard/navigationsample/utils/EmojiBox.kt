package de.genericandwildcard.navigationsample.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import de.genericandwildcard.navigationsample.ui.theme.NavigationSampleTheme

@Composable
internal fun EmojiBox(
    modifier: Modifier = Modifier,
    background: Color = rememberRandomSoftColor(),
    emoji: Emoji = rememberRandomEmoji(),
) {
    Surface(
        modifier = modifier,
        color = background
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                style = MaterialTheme.typography.displayMedium,
                text = emoji.emoji,
            )
        }
    }
}

@Preview
@Composable
private fun EmojiBoxPreview() {
    NavigationSampleTheme {
        EmojiBox()
    }
}
