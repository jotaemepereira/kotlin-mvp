package jotaemepereira.com.githubtrending.utils

import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import jotaemepereira.com.githubtrending.R

class SnackbarUtils {

    companion object {
        fun showErrorMessage(message: String, parentView: View) {
            showSnackbar(R.color.red_error, message, parentView)
        }

        private fun showSnackbar(color: Int, message: String, parentView: View) {
            val snackbar = Snackbar.make(parentView, message, Snackbar.LENGTH_LONG)
            val view = snackbar.view
            view.setBackgroundColor(ContextCompat.getColor(parentView.context, color))
            snackbar.show()
        }
    }
}