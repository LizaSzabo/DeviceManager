package hu.bme.aut.android.devicemanager.util

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

fun showSnackBar(view: View, backgroundColor: Int, message: String) {
    val snackbar = Snackbar.make(
        view, message,
        Snackbar.LENGTH_LONG
    ).setAction("Action", null)
    snackbar.setActionTextColor(Color.WHITE)

    val snackbarView = snackbar.view
    snackbarView.setBackgroundColor(backgroundColor)

    val textView =
        snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    textView.textSize = 18f
    textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
    snackbar.show()
}