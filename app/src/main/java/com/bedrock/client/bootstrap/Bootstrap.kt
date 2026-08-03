
package com.bedrock.client.bootstrap
import android.content.Context
import com.bedrock.client.loader.native.NativeLoader
import com.bedrock.client.bridge.BridgeManager
import com.bedrock.client.logger.Logger
import com.bedrock.client.minecraft.instance.InstanceManager

class Bootstrap private constructor(private val context: Context) {
    private val nativeLoader = NativeLoader()

    fun initialize(onDone: () -> Unit) {
        Logger.i("Bootstrap", "Application -> Bootstrap -> Launcher")
        try {
            nativeLoader.loadLibrary("bedrock_client") // cpp bootstrap.so
            BridgeManager.getInstance().init()
            onDone()
        } catch (e: Exception) {
            Logger.e("Bootstrap", "Init failed", e)
            onDone() // continue anyway for dev
        }
    }

    fun launchMinecraft(instance: InstanceManager.Instance, version: VersionWrapper, callback: (com.bedrock.client.launcher.Launcher.Result) -> Unit) {
        Logger.i("Bootstrap", "Launching Minecraft ${version.code} instance ${instance.id}")
        // استدعاء Native
        val pid = BridgeManager.getInstance().launchGame(instance.path.absolutePath, version.code)
        if (pid > 0) callback(com.bedrock.client.launcher.Launcher.Result.Success(pid))
        else callback(com.bedrock.client.launcher.Launcher.Result.Failure("Native launch failed"))
    }

    data class VersionWrapper(val code: String)

    companion object {
        @Volatile private var INSTANCE: Bootstrap? = null
        fun getInstance(ctx: Context) = INSTANCE ?: synchronized(this) { INSTANCE ?: Bootstrap(ctx.applicationContext).also { INSTANCE = it } }
    }
}
