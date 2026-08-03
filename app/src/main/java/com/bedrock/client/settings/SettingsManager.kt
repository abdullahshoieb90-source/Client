
package com.bedrock.client.settings
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingEntity(@PrimaryKey val key: String, val value: String)

class SettingsManager private constructor(private val context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("client_settings", Context.MODE_PRIVATE)

    fun load() { /* load from Room + prefs */ }

    fun getAll(): Map<String, String> = prefs.all.mapValues { it.value.toString() }

    fun set(key: String, value: String) { prefs.edit().putString(key, value).apply() }
    fun get(key: String, def: String = ""): String = prefs.getString(key, def) ?: def
    fun isEnabled(key: String, def: Boolean = false): Boolean = prefs.getBoolean(key, def)

    companion object {
        @Volatile private var INSTANCE: SettingsManager? = null
        fun getInstance(ctx: Context) = INSTANCE ?: synchronized(this) { INSTANCE ?: SettingsManager(ctx.applicationContext).also { INSTANCE = it } }
    }
}
