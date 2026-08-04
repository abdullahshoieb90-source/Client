package com.bedrock.client.runtime

import android.content.Context
import android.content.pm.PackageManager
import com.bedrock.client.logger.Logger

class RuntimeManager(
    private val context: Context
) {

    fun prepare(packageName: String): Boolean {

        return try {

            val app =
                context.packageManager.getApplicationInfo(packageName, 0)

            nativeSetRuntimeConfig(
                packageName,
                app.sourceDir,
                app.nativeLibraryDir,
                context.filesDir.absolutePath
            )

            Logger.i(
                "RuntimeManager",
                "Runtime prepared."
            )

            true

        } catch (e: PackageManager.NameNotFoundException) {

            Logger.e(
                "RuntimeManager",
                "Minecraft not installed."
            )

            false
        }
    }

    private external fun nativeSetRuntimeConfig(
        packageName: String,
        apkPath: String,
        nativeLibraryDir: String,
        sandboxPath: String
    )
}
