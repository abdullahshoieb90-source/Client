package com.bedrock.client.bootstrap

import android.content.Context
import com.bedrock.client.bridge.BridgeManager
import com.bedrock.client.launcher.Launcher
import com.bedrock.client.loader.native.NativeLoader
import com.bedrock.client.logger.Logger
import com.bedrock.client.minecraft.instance.InstanceManager

/** Initializes the optional native module without blocking the Android launcher. */
class Bootstrap private constructor(private val context: Context) {
    private val nativeLoader = NativeLoader()

    @Volatile
    private var nativeAvailable = false

    fun initialize(onDone: () -> Unit) {
        Logger.i("Bootstrap", "Application -> Bootstrap -> Launcher")
        try {
            nativeAvailable = nativeLoader.loadLibrary("bedrock_client")
            if (nativeAvailable) {
                BridgeManager.getInstance().init()
                Logger.i("Bootstrap", "Optional native component initialized")
            } else {
                Logger.e("Bootstrap", "Optional native component unavailable; Android launch remains enabled")
            }
        } catch (error: LinkageError) {
            nativeAvailable = false
            Logger.e("Bootstrap", "Native link failed; Android launch remains enabled", error)
        } catch (error: Exception) {
            nativeAvailable = false
            Logger.e("Bootstrap", "Native initialization failed; Android launch remains enabled", error)
        } finally {
            onDone()
        }
    }

    /**
     * Legacy native entry point retained for internal callers. Normal game launches use
     * MinecraftPackageManager so they work with the installed Android package.
     */
    fun launchMinecraft(
        instance: InstanceManager.Instance,
        version: VersionWrapper,
        callback: (Launcher.Result) -> Unit
    ) {
        if (!nativeAvailable) {
            callback(Launcher.Result.Failure("Native component is unavailable"))
            return
        }

        try {
            val pid = BridgeManager.getInstance()
                .launchGame(instance.path.absolutePath, version.code)
            if (pid > 0) {
                callback(Launcher.Result.Success(version.code, "com.mojang.minecraftpe"))
            } else {
                callback(Launcher.Result.Failure("Native component could not prepare Minecraft"))
            }
        } catch (error: LinkageError) {
            nativeAvailable = false
            Logger.e("Bootstrap", "Native launch link failed", error)
            callback(Launcher.Result.Failure("Native component is unavailable"))
        }
    }

    fun isNativeAvailable(): Boolean = nativeAvailable

    data class VersionWrapper(val code: String)

    companion object {
        @Volatile
        private var INSTANCE: Bootstrap? = null

        fun getInstance(context: Context): Bootstrap =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Bootstrap(context.applicationContext).also { INSTANCE = it }
            }
    }
}
