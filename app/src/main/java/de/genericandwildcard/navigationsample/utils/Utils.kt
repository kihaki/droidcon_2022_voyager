package de.genericandwildcard.navigationsample.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.os.Parcel
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.parcelize.Parceler

/**
 * Allows setting a modifier if predicate is true. Will do nothing if predicate is false.
 */
@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.modifyIf(
    predicate: Boolean,
    modification: @Composable Modifier.() -> Modifier,
) = if (predicate) composed(factory = modification) else this

/**
 * Allows finding the local activity in compose
 */
val LocalActivity @Composable get() = LocalContext.current.activity

val Context.activity: ComponentActivity
    get() = when (this) {
        is ContextWrapper -> activity
        else -> error("No AppCompatActivity found in current context: $this")
    }

val ContextWrapper.activity: ComponentActivity
    get() = when (this) {
        is ComponentActivity -> this
        else -> baseContext.activity
    }

fun <T : Any> mutableStateSaver() = Saver<MutableState<T>, T>(
    save = { state -> state.value },
    restore = { value -> mutableStateOf(value) },
)

fun colorSaver() = Saver<Color, Long>(
    save = { it.value.toLong() },

    // Do not use Color(it) directly here,
    // With the Jetpack Overload that takes Long it will not restore as required,
    // because the Color constructor will do numbers magic which
    // we don't want (since we will restore a previously saved value).
    // If you do, the restored color will be transparent, which is no bueno.
    restore = { Color(it.toULong()) }
)

object ColorParceler : Parceler<Color> {
    // Do not use Color(it) directly here,
    // With the Jetpack Overload that takes Long it will not restore as required,
    // because the Color constructor will do numbers magic which
    // we don't want (since we will restore a previously saved value).
    // If you do, the restored color will be transparent, which is no bueno.
    override fun create(parcel: Parcel) = Color(parcel.readLong().toULong())
    override fun Color.write(parcel: Parcel, flags: Int) = parcel.writeLong(value.toLong())
}

fun Navigator.findRoot(): Navigator = parent?.findRoot() ?: this