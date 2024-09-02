package com.bridge.androidtechnicaltest.util

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.bridge.androidtechnicaltest.R

private var alertDialog: AlertDialog? = null
private var builder: AlertDialog.Builder? = null


fun showProgressDialog(context: Context) {
    if (builder == null) {
        builder = AlertDialog.Builder(context)
    }
    builder?.setView(R.layout.custom_progress_bar)
    alertDialog = builder?.create()
    alertDialog?.setCancelable(false)
    alertDialog?.show()
}

fun hideProgressDialog(view: View) {
    if (alertDialog != null) {
        println("hide dialog")
        view.isEnabled = true
        alertDialog!!.dismiss()
    }
    builder = null
}