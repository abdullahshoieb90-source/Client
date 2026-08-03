package com.bedrock.client.minecraft.`package`

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

class MinecraftPackageManager(context: Context) {

    private val appContext = context.applicationContext
    private val packageManager = appContext.packageManager

    data class Installation(
        val packageName: String,
        val versionName: String,
        val versionCode: Long,
        val enabled: Boolean,

        // Paths used by the native bootstrap
        val sourceDir: String,
        val nativeLibraryDir: String,
        val dataDir: String?,
        val publicSourceDir: String
    )

    fun getInstallation(): Installation? {
        val packageInfo = getPackageInfo() ?: return null
        val appInfo = packageInfo.applicationInfo ?: return null

        val versionName = packageInfo.versionName
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?: packageVersionCode(packageInfo).toString()

        return Installation(
            packageName = packageInfo.packageName,
            versionName = versionName,
            versionCode = packageVersionCode(packageInfo),
            enabled = appInfo.enabled,

            sourceDir = appInfo.sourceDir,
            nativeLibraryDir = appInfo.nativeLibraryDir,
            dataDir = appInfo.dataDir,
            publicSourceDir = appInfo.publicSourceDir
        )
    }

    fun isInstalled(): Boolean =
        getInstallation() != null

    fun getVersion(): String? =
        getInstallation()?.versionName

    fun getNativeLibraryDir(): String? =
        getInstallation()?.nativeLibraryDir

    fun getSourceDir(): String? =
        getInstallation()?.sourceDir

    fun getDataDir(): String? =
        getInstallation()?.dataDir

    fun getApplicationInfo(): ApplicationInfo? =
        getPackageInfo()?.applicationInfo

    /**
     * Launch Minecraft using Android.
     */
    fun launchInstalled(): Result<Installation> {

        val installation = getInstallation()
            ?: return Result.failure(
                IllegalStateException("Minecraft is not installed.")
            )

        if (!installation.enabled) {
            return Result.failure(
                IllegalStateException("Minecraft is disabled.")
            )
        }

        val launchIntent =
            packageManager.getLaunchIntentForPackage(installation.packageName)
                ?: return Result.failure(
                    IllegalStateException("Unable to find Minecraft launcher activity.")
                )

        return runCatching {

            launchIntent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            )

            appContext.startActivity(launchIntent)

            installation
        }
    }

    @Suppress("DEPRECATION")
    private fun getPackageInfo(): PackageInfo? {
        return try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                packageManager.getPackageInfo(
                    MINECRAFT_PACKAGE,
                    PackageManager.PackageInfoFlags.of(0)
                )

            } else {

                packageManager.getPackageInfo(
                    MINECRAFT_PACKAGE,
                    0
                )

            }

        } catch (_: Exception) {
            null
        }
    }

    @Suppress("DEPRECATION")
    private fun packageVersionCode(packageInfo: PackageInfo): Long {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode
        } else {
            packageInfo.versionCode.toLong()
        }

    }

    companion object {

        const val MINECRAFT_PACKAGE = "com.mojang.minecraftpe"

    }
}
