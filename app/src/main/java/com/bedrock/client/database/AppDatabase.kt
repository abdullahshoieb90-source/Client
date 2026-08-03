
package com.bedrock.client.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.bedrock.client.account.AccountEntity
import com.bedrock.client.minecraft.profile.ProfileEntity
import com.bedrock.client.settings.SettingEntity

@Database(entities = [AccountEntity::class, ProfileEntity::class, SettingEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun profileDao(): ProfileDao
    abstract fun settingsDao(): SettingsDao
}
