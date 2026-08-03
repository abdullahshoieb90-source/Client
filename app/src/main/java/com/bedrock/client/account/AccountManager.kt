
package com.bedrock.client.account
import android.content.Context

class AccountManager private constructor(private val context: Context) {
    fun getActiveAccount(): AccountEntity? = null // TODO from DB
    fun loginOffline(username: String): AccountEntity = AccountEntity(java.util.UUID.randomUUID().toString(), AccountType.OFFLINE, username, null, null)

    companion object {
        @Volatile private var INSTANCE: AccountManager? = null
        fun getInstance(ctx: Context) = INSTANCE ?: synchronized(this) { INSTANCE ?: AccountManager(ctx).also { INSTANCE = it } }
    }
}
