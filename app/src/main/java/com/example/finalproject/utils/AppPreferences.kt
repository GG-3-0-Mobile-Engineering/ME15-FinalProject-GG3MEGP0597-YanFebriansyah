package com.example.finalproject.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val PREFERENCES_NAME = "MyAppPreferences"
    private const val KEY_NIGHT_MODE = "night_mode"

    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun isNightModeOn(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_NIGHT_MODE, false)
    }

    fun setNightMode(context: Context, isNightModeOn: Boolean) {
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(KEY_NIGHT_MODE, isNightModeOn)
        editor.apply()
    }
}