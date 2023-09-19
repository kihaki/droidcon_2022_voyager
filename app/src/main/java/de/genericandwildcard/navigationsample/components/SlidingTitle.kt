package de.genericandwildcard.navigationsample.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SlidingTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        modifier = modifier
            .padding(all = 16.dp)
            .fillMaxWidth(),
        transitionSpec = {
            (slideInVertically { it } + fadeIn()) togetherWith slideOutVertically { -it } + fadeOut()
        },
        targetState = title,
        label = "sliding-title-anim",
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = it,
        )
    }
}