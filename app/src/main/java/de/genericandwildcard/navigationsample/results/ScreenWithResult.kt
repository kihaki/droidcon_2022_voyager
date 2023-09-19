package de.genericandwildcard.navigationsample.results

import android.os.Parcelable
import cafe.adriel.voyager.core.screen.Screen

interface ScreenResult : Parcelable

// Marks a Screen as a Screen returning a result
interface ScreenWithResult<T : ScreenResult> : Screen
