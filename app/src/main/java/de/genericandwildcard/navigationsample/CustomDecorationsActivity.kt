package de.genericandwildcard.navigationsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.navigator.Navigator
import de.genericandwildcard.navigationsample.screens.EmojiScreen
import de.genericandwildcard.navigationsample.transition.component.ScreenBasedTransition
import de.genericandwildcard.navigationsample.ui.theme.NavigationSampleTheme

class CustomDecorationsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            NavigationSampleTheme(fullScreen = true) {
                Box(modifier = Modifier.background(color = Color.Black)) {
                    Navigator(
                        screen = EmojiScreen(shoutOutName = "Mom"),
                    ) { navigator ->
                        ScreenBasedTransition(navigator = navigator)
                    }
                }
            }
        }
    }
}