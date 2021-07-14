package com.swvl.assessmenttest.utils

import android.app.Activity
import android.content.Intent

fun <T> Activity.finishAndNavigateTo(target: Class<out T>) {
    Intent(this, target)
        .apply {
            startActivity(this)
            finish()
        }
}
