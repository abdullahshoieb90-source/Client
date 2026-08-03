
package com.bedrock.client.minecraft.profile
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class ProfileEntity(@PrimaryKey val id: String, val name: String, val options: String)
