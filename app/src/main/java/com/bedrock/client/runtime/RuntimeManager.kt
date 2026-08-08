package com.bedrock.client.runtime

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.bedrock.client.logger.Logger
import com.bedrock.client.minecraft.instance.InstanceManager
import com.bedrock.client.minecraft.pkg.FakeApplicationInfoFactory

/**
 * Pushes native library / apk / sandbox paths down into cpp/runtime/RuntimeConfig via JNI,
 * so C++ (Bootstrap::loadOriginalMinecraft / LibraryLoader) always resolves
 * libminecraftpe.so from the *correct instance-specific* absolute path — never a
 * bare system-wide dlopen.
 */
class RuntimeManager(private val context: Context) {

    /** Installed-app path: Minecraft is present via PackageManager. */
    fun prepare(packageName: String, sandboxPath: String): Boolean {
        return try {
            val app = context.packageManager.getApplicationInfo(packageName, 0)
            pushConfig(packageName, app, sandboxPath)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            Logger.e("RuntimeManager", "Minecraft not installed.")
            false
        }
    }

    /**
     * Install-free path: Minecraft was imported via ApkImportManager into [instanceId] and
     * never registered with PackageManager. Builds the same shape of config from the
     * instance's own isolated apk/libs/data dirs instead.
     */
    fun prepareImported(instanceId: String): Boolean {
        val instance = InstanceManager.getInstance(context).getInstance(instanceId)
        val fakeInfo = FakeApplicationInfoFactory.build(instance)
        if (fakeInfo == null) {
            Logger.e("RuntimeManager", "No apk imported for instance $instanceId.")
            return false
        }
        pushConfig(fakeInfo.packageName, fakeInfo, instance.path.absolutePath)
        return true
    }

    private fun pushConfig(packageName: String, app: ApplicationInfo, sandboxPath: String) {
        nativeSetRuntimeConfig(
            packageName,
            app.sourceDir,
            app.nativeLibraryDir,
            sandboxPath
        )
        Logger.i(
            "RuntimeManager",
            "Runtime config set: apk=${app.sourceDir}, libs=${app.nativeLibraryDir}, sandbox=$sandboxPath"
        )
    }

    private external fun nativeSetRuntimeConfig(
        packageName: String,
        apkPath: String,
        nativeLibraryDir: String,
        sandboxPath: String
    )

    companion object {
        init {
            // Same native lib the rest of the bridge (BridgeManager) loads; RuntimeJNI.cpp
            // is compiled into it per cpp/CMakeLists.txt.
            System.loadLibrary("bedrock_client")
        }
    }
}

