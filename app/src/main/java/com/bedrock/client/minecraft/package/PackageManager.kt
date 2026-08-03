package com.bedrock.client.minecraft.`package`

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

/**
 * Reads and launches the Minecraft installation that Android already knows about.
 *
 * Minecraft runs in its own Android process. Loading libminecraftpe.so directly from
 * V Client's process is blocked by Android's app/linker isolation on modern devices,
 * so the supported launch path is the package's launcher activity.
 */
class MinecraftPackageManager(context: Context) {
    private val appContext = context.applicationContext
    private val packageManager = appContext.packageManager

    data class Installation(
        val packageName: String,
        val versionName: String,
        val versionCode: Long,
        val enabled: Boolean
    )

    fun getInstallation(): Installation? {
        val packageInfo = getPackageInfo() ?: return null
        val versionName = packageInfo.versionName
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?: packageVersionCode(packageInfo).toString()

        return Installation(
            packageName = packageInfo.packageName,
            versionName = versionName,
            versionCode = packageVersionCode(packageInfo),
            enabled = packageInfo.applicationInfo?.enabled != false
        )
    }

    fun isInstalled(): Boolean = getInstallation() != null

    fun getVersion(): String? = getInstallation()?.versionName

    /** Launches the installed Minecraft app through its real Android entry point. */
    fun launchInstalled(): Result<Installation> {
        val installation = getInstallation()
            ?: return Result.failure(IllegalStateException("Minecraft is not installed on this device"))

        if (!installation.enabled) {
            return Result.failure(IllegalStateException("Minecraft is disabled on this device"))
        }

        val launchIntent = packageManager.getLaunchIntentForPackage(installation.packageName)
            ?: return Result.failure(IllegalStateException("Minecraft has no launchable activity"))

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
    private fun getPackageInfo(): PackageInfo? = try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(
                MINECRAFT_PACKAGE,
                PackageManager.PackageInfoFlags.of(0L)
            )
        } else {
            packageManager.getPackageInfo(MINECRAFT_PACKAGE, 0)
        }
    } catch (_: PackageManager.NameNotFoundException) {
        null
    } catch (_: SecurityException) {
        null
    }

    @Suppress("DEPRECATION")
    private fun packageVersionCode(packageInfo: PackageInfo): Long =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode
        } else {
            packageInfo.versionCode.toLong()
        }

    companion object {
        const val MINECRAFT_PACKAGE = "com.mojang.minecraftpe"
    }
}
