package com.soomapps.universalremotecontrol.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*


class LanguageHelper {
    private val GENERAL_STORAGE = "GENERAL_STORAGE"
    private val KEY_USER_LANGUAGE = "KEY_USER_LANGUAGE"

    /**
     * Update the app language
     *
     * @param language Language to switch to.
     */
    fun updateLanguage(context: Context, language: String?) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res: Resources = context.getResources()
        val cfg = Configuration(res.getConfiguration())
        cfg.locale = locale
        res.updateConfiguration(cfg, res.getDisplayMetrics())
    }

    /**
     * Store the language selected by the user.
     * /!\ SHOULD BE CALLED WHEN THE USER CHOOSE THE LANGUAGE
     */
    fun storeUserLanguage(context: Context, language: String?) {
        context.getSharedPreferences(GENERAL_STORAGE, MODE_PRIVATE)
            .edit()
            .putString(KEY_USER_LANGUAGE, language)
            .apply()
    }

    /**
     * @return The stored user language or null if not found.
     */
    fun getUserLanguage(context: Context): String {
        return context.getSharedPreferences(GENERAL_STORAGE, MODE_PRIVATE)
            .getString(KEY_USER_LANGUAGE, null)!!
    }
}