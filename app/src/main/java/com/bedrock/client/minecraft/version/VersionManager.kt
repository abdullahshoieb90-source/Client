
package com.bedrock.client.minecraft.version
import android.content.Context

class VersionManager private constructor(private val context: Context) {
    data class MinecraftVersion(val code: String, val isSupported: Boolean, val abi: String)

    fun getActiveVersion(): MinecraftVersion = MinecraftVersion("1.21.100", true, "arm64-v8a")
    fun isCompatible(v: MinecraftVersion): Boolean = v.isSupported
    fun getAllVersions(): List<MinecraftVersion> = listOf(MinecraftVersion("1.21.100", true, "arm64-v8a"), MinecraftVersion("1.21.130", true, "arm64-v8a"))

    companion object {
        @Volatile private var INSTANCE: VersionManager? = null
        fun getInstance(ctx: Context) = INSTANCE ?: synchronized(this) { INSTANCE ?: VersionManager(ctx).also { INSTANCE = it } }
    }
}
