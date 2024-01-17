package com.aakansha.myapplication.utils

import android.content.Context
import android.content.SharedPreferences

object PrefUtils {
    operator fun get(context: Context): SharedPreferences =
        context.getSharedPreferences("APP_PREFERENCES", 0)

    private fun setPref(context: Context, putValue: (SharedPreferences.Editor) -> Unit) {
        get(context).apply {
            with(edit()) {
                putValue(this)
                apply()
            }
        }
    }

    fun setBooleanPref(context: Context, key: String, value: Boolean) {
        setPref(context) { editor ->
            editor.putBoolean(key, value)
        }
    }

    fun getBooleanPref(context: Context, key: String, defaultValue: Boolean = false): Boolean =
        get(context).getBoolean(key, defaultValue)

    fun setStringPref(context: Context, key: String, value: String?) {
        setPref(context) { editor ->
            editor.putString(key, value)
        }
    }

    fun getStringPref(context: Context, key: String): String? =
        get(context).getString(key, "")

    fun setIntPref(context: Context, key: String, value: Int) {
        setPref(context) { editor ->
            editor.putInt(key, value)
        }
    }

    fun getIntPref(context: Context, key: String): Int =
        get(context).getInt(key, 0)
}