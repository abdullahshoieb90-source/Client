
package com.bedrock.client.database
import androidx.room.*
import com.bedrock.client.account.AccountEntity
import com.bedrock.client.minecraft.profile.ProfileEntity
import com.bedrock.client.settings.SettingEntity

@Dao interface AccountDao { @Query("SELECT * FROM accounts") suspend fun getAll(): List<AccountEntity>; @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(a: AccountEntity); @Delete suspend fun delete(a: AccountEntity) }
@Dao interface ProfileDao { @Query("SELECT * FROM profiles") suspend fun getAll(): List<ProfileEntity> }
@Dao interface SettingsDao { @Query("SELECT * FROM settings") suspend fun getAll(): List<SettingEntity>; @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(s: SettingEntity) }
