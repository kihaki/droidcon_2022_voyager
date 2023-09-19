package de.genericandwildcard.navigationsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.genericandwildcard.navigationsample.results.ScreenResult
import de.genericandwildcard.navigationsample.results.ScreenResultsStore
import de.genericandwildcard.navigationsample.results.ScreenWithResult
import de.genericandwildcard.navigationsample.results.component.rememberScreenResult
import de.genericandwildcard.navigationsample.results.component.rememberScreenWithResultLauncher
import de.genericandwildcard.navigationsample.results.setScreenResult
import de.genericandwildcard.navigationsample.transition.CustomScreenTransition
import de.genericandwildcard.navigationsample.transition.SlideUpTransition
import de.genericandwildcard.navigationsample.transition.component.ScreenBasedTransition
import de.genericandwildcard.navigationsample.ui.theme.NavigationSampleTheme
import kotlinx.parcelize.Parcelize

class TypesafeScreenResultsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationSampleTheme {
                Navigator(
                    screen = CustomTransitionsBottomBarWrapperScreen(
                        initialScreen = ResultReceiverScreen,
                    ),
                ) {
                    ScreenResultsStore()
                    ScreenBasedTransition(navigator = it)
                }
            }
        }
    }
}

object ResultReceiverScreen : Screen {

    @Composable
    override fun Content() {

        val textInputScreen = remember { TextInputScreen }

        val resultFromNextScreen by rememberScreenResult<TextInputScreen.Result>(
            resultSourceKey = textInputScreen.key,
        )

        val screenResultLauncher = rememberScreenWithResultLauncher(
            screen = textInputScreen
        ) {
            // We could react to the result here, but we are not going to!
        }

        Surface {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = resultFromNextScreen?.let { "Previous Screen says: ${it.message}" }
                        ?: "No result set yet"
                )

                ElevatedButton(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = { screenResultLauncher.launch() },
                ) {
                    Text("Open the Form")
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

object TextInputScreen :
    ScreenWithResult<TextInputScreen.Result>,
    CustomScreenTransition by SlideUpTransition {

    @Parcelize
    data class Result(
        val message: String,
    ) : ScreenResult

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Surface {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.weight(1f))

                var message by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = message,
                    label = { Text("Enter your message here") },
                    onValueChange = {
                        message = it
                        //                    setScreenResult(Result(it))
                    }
                )

                ElevatedButton(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = { navigator.pop() },
                ) {
                    Text("Abort")
                }

                ElevatedButton(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = {
                        setScreenResult(Result(message))
                        navigator.pop()
                    },
                ) {
                    Text("Set result and close")
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

