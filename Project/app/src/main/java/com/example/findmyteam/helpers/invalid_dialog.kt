package com.example.findmyteam.helpers

import android.app.AlertDialog
import android.content.Context

fun showInvalidDialog(title: String, message: String, context: Context) {
        val alertDialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
}
