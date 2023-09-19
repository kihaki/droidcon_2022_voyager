package de.genericandwildcard.navigationsample.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomButtonsScaffold(
    onNext: () -> Unit,
    onPrevious: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            content()
        }
        BottomBar(
            onNext = onNext,
            onPrevious = onPrevious,
        )
    }
}

@Composable
fun BottomBar(
    onNext: () -> Unit,
    onPrevious: (() -> Unit)? = null,
) {
    Surface {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
        ) {
            AnimatedVisibility(visible = onPrevious != null) {
                FilledTonalIconButton(
                    modifier = Modifier
                        .widthIn(min = 100.dp, max = 200.dp)
                        .padding(end = 8.dp),
                    onClick = { onPrevious?.invoke() },
                ) {
                    Text("< Back")
                }
            }
            FilledIconButton(
                modifier = Modifier.weight(1f),
                onClick = onNext,
            ) {
                Text("Next")
            }
        }
    }
}