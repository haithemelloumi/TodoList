package com.helloumi.ui.utils.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.displayToast(@StringRes textToDisplay: Int): Toast {
    return Toast.makeText(this, getString(textToDisplay), Toast.LENGTH_SHORT).apply { show() }
}
