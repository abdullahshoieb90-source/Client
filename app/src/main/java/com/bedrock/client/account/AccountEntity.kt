
package com.bedrock.client.account
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey val id: String,
    val type: AccountType,
    val username: String,
    val accessToken: String?,
    val refreshToken: String?
)
enum class AccountType { OFFLINE, MICROSOFT, XBOX }
