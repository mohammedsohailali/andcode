package com.android.realapp.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.weather.info.util.AutoClearedValue


/**
 * Throws Exception from [Any] Object.
 */
@Suppress("unused")
fun <E : Throwable> Any.logException(
    exception: E,
    message: String = exception.message ?: "No message"
) = Log.e(
    this.javaClass.simpleName, message, exception
)

/**
 * Extension fun to Log Debug
 */
@Suppress("unused")
fun logD(message: String, tag: String = "SPApp") = Log.d(tag, message)

/**
 * Extension fun to Log Error
 */
@Suppress("unused")
fun logE(message: String, tag: String = "SPApp") = Log.e(tag, message)

/**
 * Extension fun to show Error with String to [EditText]
 */
@Suppress("unused")
fun EditText.setErrorWithFocus(message: String) {
    this.error = message
    this.requestFocus()
}

/**
 * Extension fun to Return get [EditText.value] with [String.trim]
 */
fun EditText.value() = text.toString().trim()

/**
 * Extension fun to Return get [EditText.length]
 */
@Suppress("EXTENSION_SHADOWED_BY_MEMBER", "unused")
fun EditText.length() = text.toString().trim().length

/**
 * Extension fun to Return get Color Resource Id in [Int]
 */
@Suppress("unused")
fun Context.getContextCompactColor(colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}


/**
 * To Enable and Disable a [View] from XML DataBinding
 */
//@Suppress("unused")
//@BindingAdapter("enableDisable")
//fun enableDisable(view: View, enable: Boolean) {
//    view.isEnabled = enable
//}

/**
 * Show Visibility of a [View]
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

/**
 * Hide Visibility of a [View]
 */
fun View.hide() {
    this.visibility = View.GONE
}

/**
 * Inflates view
 */
@Suppress("unused")
fun android.view.ViewGroup.inflate(layoutId: Int): View {
    return android.view.LayoutInflater.from(context).inflate(layoutId, this, false)
}

/**
 * Makes SnackBar which comes from Bottom for the Short duration from [Activity]
 */
@Suppress("unused")
fun Activity.makeToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

/**
 * Makes SnackBar which comes from Bottom for the Short duration from [Fragment]
 */
@Suppress("unused")
fun Fragment.makeToast(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

/**
 * Extension fun to convert PX to DP
 */
val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Extension fun to convert DP to PX
 */
val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

/**
 * Creates an [AutoClearedValue] associated with this fragment.
 */
fun <T : Any> Fragment.autoCleared() = AutoClearedValue<T>(this)

/**
 * Makes SnackBar which comes from Bottom for the Short duration
 */
fun makeSnackBar(text: String, view: View?) {
    val snackBar = view?.let { Snackbar.make(it, text, Snackbar.LENGTH_LONG) }
    snackBar?.show()
}

fun Double.kelvinToCelsius(): Int {
    return (this - 273.15).toInt()
}
