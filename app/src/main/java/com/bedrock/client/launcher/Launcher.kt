package com.bedrock.client.launcher

import android.content.Context
import com.bedrock.client.R
import com.bedrock.client.environment.sandbox.MinecraftEnvironmentManager
import com.bedrock.client.logger.Logger
import com.bedrock.client.minecraft.`package`.MinecraftPackageManager
import com.bedrock.client.minecraft.version.VersionManager
import com.bedrock.client.settings.SettingsManager

/** Coordinates private-environment preparation and opens the installed Minecraft app. */
class Launcher(context: Context) {
    private val appContext = context.applicationContext
    private val versionManager = VersionManager.getInstance(appContext)
    private val minecraftPackageManager = MinecraftPackageManager(appContext)
    private val environmentManager = MinecraftEnvironmentManager(appContext)

    fun launch(options: LaunchOptions, callback: (Result) -> Unit) {
        Logger.i("Launcher", "Starting launch sequence: $options")

        try {
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
            val environment = environmentManager.prepare(
                instanceId = options.instanceId,
                syncToSharedStorage = options.syncToSharedStorage
            )

            environment.notes.forEach { note ->
                Logger.i("Launcher", note)
            }

            if (options.requireSynchronizedEnvironment && !environment.synchronizedToSharedStorage) {
                callback(
                    Result.Failure(
                        "Private environment could not be synchronized to shared Minecraft storage on this device."
                    )
                )
                return
            }

            minecraftPackageManager.launchInstalled().fold(
                onSuccess = { installation ->
                    Logger.i(
                        "Launcher",
                        "Opened ${installation.packageName} ${installation.versionName} with instance ${environment.instance.id}"
                    )
                    callback(
                        Result.Success(
                            version = installation.versionName,
                            packageName = installation.packageName,
                            instanceId = environment.instance.id,
                            sandboxPath = environment.sandbox.root.absolutePath,
                            exportTargetPath = environment.activeStorageDir?.absolutePath,
                            environmentSynchronized = environment.synchronizedToSharedStorage
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
        val enableOverlay: Boolean = true,
        val syncToSharedStorage: Boolean = true,
        val requireSynchronizedEnvironment: Boolean = false
    )

    sealed class Result {
        data class Success(
            val version: String,
            val packageName: String,
            val instanceId: String,
            val sandboxPath: String,
            val exportTargetPath: String?,
            val environmentSynchronized: Boolean
        ) : Result()

        data class Failure(val reason: String) : Result()
    }
}
