package com.vclient.runtime

class RuntimeLoader(
    private val config: RuntimeConfig
) {

    fun load(): Boolean {
        return try {
            verifyConfig()

            // سيتم استدعاء NativeBridge هنا لاحقًا
            // NativeBridge.initialize(config)
            // NativeBridge.loadLibraries()

            true
        } catch (e: Exception) {
            false
        }
    }

    private fun verifyConfig() {
        require(config.apkPath.isNotBlank()) {
            "Minecraft APK path is empty."
        }

        require(config.nativeLibraryDir.exists()) {
            "Native library directory does not exist."
        }

        require(config.sandboxDir.exists()) {
            "Sandbox directory does not exist."
        }
    }
}
