package com.example.mvvmdaggerrx.data.local

import android.content.Context
import com.example.mvvmdaggerrx.data.local.sharedpreference.SharedPreferencesUtils
import javax.inject.Inject

class LocalDataUtils @Inject constructor(private val context: Context) {

    val sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context)

    fun getString(id: Int): String {
        return context.getString(id)
    }
}