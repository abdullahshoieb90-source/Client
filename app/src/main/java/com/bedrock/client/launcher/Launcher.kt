package com.bedrock.client.launcher

import android.content.Context
import com.bedrock.client.R
import com.bedrock.client.environment.sandbox.SandboxManager
import com.bedrock.client.logger.Logger
import com.bedrock.client.minecraft.`package`.MinecraftPackageManager
import com.bedrock.client.minecraft.version.VersionManager
import com.bedrock.client.settings.SettingsManager

/** Coordinates validation and opens the Minecraft installation on the device. */
class Launcher(context: Context) {
    private val appContext = context.applicationContext
    private val sandboxManager = SandboxManager(appContext)
    private val versionManager = VersionManager.getInstance(appContext)
    private val minecraftPackageManager = MinecraftPackageManager(appContext)

    fun launch(options: LaunchOptions, callback: (Result) -> Unit) {
        Logger.i("Launcher", "Starting launch sequence: $options")

        try {
            // Android package metadata is the source of truth; no hard-coded game version.
            val version = versionManager.getActiveVersion()
            if (version == null) {
                callback(Result.Failure(appContext.getString(R.string.minecraft_not_installed)))
                return
            }

            if (!versionManager.isCompatible(version)) {
                callback(
                    Result.Failure(
                        appContext.getString(R.string.minecraft_disabled, version.code)
                    )
                )
                return
            }

            SettingsManager.getInstance(appContext).getAll()
            sandboxManager.createSandbox(options.instanceId)

            // Do not dlopen libminecraftpe.so from this app. Android isolates native
            // libraries per package, so launch Minecraft through its package activity.
            minecraftPackageManager.launchInstalled().fold(
                onSuccess = { installation ->
                    Logger.i(
                        "Launcher",
                        "Opened ${installation.packageName} ${installation.versionName}"
                    )
                    callback(
                        Result.Success(
                            version = installation.versionName,
                            packageName = installation.packageName
                        )
                    )
                },
                onFailure = { error ->
                    Logger.e("Launcher", "Minecraft launch failed", error)
                    callback(
                        Result.Failure(
                            error.message ?: appContext.getString(R.string.launch_unknown_error)
                        )
                    )
                }
            )
        } catch (error: Exception) {
            Logger.e("Launcher", "Launch failed", error)
            callback(
                Result.Failure(error.message ?: appContext.getString(R.string.launch_unknown_error))
            )
        }
    }

    data class LaunchOptions(
        val instanceId: String = "default",
        val enableModules: Boolean = true,
        val enableOverlay: Boolean = true
    )

    sealed class Result {
        data class Success(val version: String, val packageName: String) : Result()
        data class Failure(val reason: String) : Result()
    }
}
