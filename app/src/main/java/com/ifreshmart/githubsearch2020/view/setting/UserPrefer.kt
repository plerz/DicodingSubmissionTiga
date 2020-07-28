package com.ifreshmart.githubsearch2020.view.setting

import android.content.Context

class UserPrefer(context: Context) {
    companion object{
        private const val PREFS_NAME = "user_pref"
        private const val DAILY_REMINDER = "daily_reminder"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setDailyReminder(state: Boolean){
        val editor = preferences.edit()
        editor.putBoolean(DAILY_REMINDER, state)
        editor.apply()
    }
}