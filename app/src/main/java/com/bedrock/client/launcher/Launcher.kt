
package com.bedrock.client.launcher

import android.content.Context
import com.bedrock.client.bootstrap.Bootstrap
import com.bedrock.client.environment.sandbox.SandboxManager
import com.bedrock.client.minecraft.version.VersionManager
import com.bedrock.client.minecraft.instance.InstanceManager
import com.bedrock.client.settings.SettingsManager
import com.bedrock.client.logger.Logger

/**
 * القلب الرئيسي للبرنامج
 * وظيفته:
 * - بدء تشغيل التطبيق
 * - تهيئة جميع الأنظمة
 * - تحميل الإعدادات
 * - تجهيز بيئة Minecraft
 * - استدعاء Bootstrap
 */
class Launcher(private val context: Context) {
    private val sandboxManager = SandboxManager(context)
    private val versionManager = VersionManager.getInstance(context)
    private val instanceManager = InstanceManager.getInstance(context)

    fun launch(options: LaunchOptions, callback: (Result) -> Unit) {
        Logger.i("Launcher", "Starting launch sequence: $options")
        try {
            // 1. Settings + Database
            val settings = SettingsManager.getInstance(context).getAll()

            // 2. Environment
            sandboxManager.createSandbox(options.instanceId)

            // 3. Minecraft checks
            val version = versionManager.getActiveVersion()
            if (!versionManager.isCompatible(version)) {
                callback(Result.Failure("Version not compatible: $version"))
                return
            }

            // 4. Loader
            val instance = instanceManager.getInstance(options.instanceId)

            // 5. Bridge JNI -> cpp/bootstrap
            Bootstrap.getInstance(context).launchMinecraft(instance, version, callback)

        } catch (e: Exception) {
            Logger.e("Launcher", "Launch failed", e)
            callback(Result.Failure(e.message ?: "Unknown error"))
        }
    }

    data class LaunchOptions(
        val instanceId: String = "default",
        val enableModules: Boolean = true,
        val enableOverlay: Boolean = true
    )

    sealed class Result {
        data class Success(val pid: Int) : Result()
        data class Failure(val reason: String) : Result()
    }
}
