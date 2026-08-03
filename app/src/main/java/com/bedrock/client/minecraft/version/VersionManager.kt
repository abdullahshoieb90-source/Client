package com.bedrock.client.minecraft.version

import android.content.Context
import android.os.Build
import com.bedrock.client.minecraft.`package`.MinecraftPackageManager

/** Uses Android package metadata as the single source of truth for the active version. */
class VersionManager private constructor(context: Context) {
    private val packageManager = MinecraftPackageManager(context.applicationContext)

    data class MinecraftVersion(
        val code: String,
        val isSupported: Boolean,
        val abi: String,
        val packageName: String,
        val packageVersionCode: Long
    )

    /** Returns the exact Minecraft version installed on this device, or null if absent. */
    fun getActiveVersion(): MinecraftVersion? {
        val installation = packageManager.getInstallation() ?: return null
        return MinecraftVersion(
            code = installation.versionName,
            isSupported = installation.enabled,
            abi = Build.SUPPORTED_ABIS.firstOrNull() ?: "unknown",
            packageName = installation.packageName,
            packageVersionCode = installation.versionCode
        )
    }

    fun isCompatible(version: MinecraftVersion): Boolean =
        version.isSupported && version.code.isNotBlank()

    fun getAllVersions(): List<MinecraftVersion> = listOfNotNull(getActiveVersion())

    companion object {
        @Volatile
        private var INSTANCE: VersionManager? = null

        fun getInstance(context: Context): VersionManager =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: VersionManager(context.applicationContext).also { INSTANCE = it }
            }
    }
}
