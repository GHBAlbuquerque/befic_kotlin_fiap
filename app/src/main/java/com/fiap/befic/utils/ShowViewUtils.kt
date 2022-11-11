package com.fiap.befic.utils

import android.view.View

class ShowViewUtils {
    companion object {
        fun showHide(view: View) {
            view.visibility = if (view.visibility == View.VISIBLE) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        }

        fun hide(view: View) {
            view.visibility = View.INVISIBLE
        }

        fun show(view: View) {
            view.visibility = View.VISIBLE
        }
    }
}