package com.example.mvvmdaggerrx.data.local.sharedpreference

import android.content.Context
import com.example.mvvmdaggerrx.utils.extensions.getSharedPref
import com.example.mvvmdaggerrx.utils.locale.LocaleLanguage

class SharedPreferencesUtils private constructor(private val prefHelper: PrefHelper) {

    fun getLanguage(): String {
        return prefHelper.getString(LANGUAGE, null)
            ?: LocaleLanguage.getDefaultLanguage()
    }

    fun setLanguage(language: String) {
        prefHelper.putObject(LANGUAGE, language)
    }

    companion object {
        const val LANGUAGE = "language"
        const val USER = "user"

        private var sharedPreferencesUtils: SharedPreferencesUtils? = null

        fun getInstance(context: Context): SharedPreferencesUtils {
            if (sharedPreferencesUtils == null)
                sharedPreferencesUtils = SharedPreferencesUtils(PrefHelper(context.getSharedPref()))

            return sharedPreferencesUtils!!
        }
    }
}