package com.bedrock.client.database

import androidx.room.TypeConverter
import com.bedrock.client.account.AccountType

/** Converts Room values that are represented by domain enums. */
class RoomConverters {
    @TypeConverter
    fun accountTypeToString(value: AccountType): String = value.name

    @TypeConverter
    fun stringToAccountType(value: String): AccountType = AccountType.valueOf(value)
}
