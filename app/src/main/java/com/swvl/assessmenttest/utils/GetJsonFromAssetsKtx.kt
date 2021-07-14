package com.swvl.assessmenttest.utils

import android.content.Context


object ReadFileFromAssets {

    fun getJsonFromAssets(context: Context): String {
        return  context.assets.open("movies.json").bufferedReader().use {it.readText()}
    }

}