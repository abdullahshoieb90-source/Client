
package com.bedrock.client.preference
import android.content.Context

class PreferenceManager(private val context: Context) {
    private val prefs = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
    fun put(key: String, value: Any) {
        prefs.edit().apply {
            when(value) { is String -> putString(key, value); is Boolean -> putBoolean(key, value); is Int -> putInt(key, value) }
        }.apply()
    }
    fun getString(key: String) = prefs.getString(key, null)
}
