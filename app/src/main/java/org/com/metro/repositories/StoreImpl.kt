package org.com.metro.repositories

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StoreImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val sharedPreferences: SharedPreferences,
) : Store {
    override fun getValue(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    override fun setValue(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}