
package com.bedrock.client.bridge

/**
 * يربط Kotlin مع C++ عبر JNI
 * Java -> JNI -> C++
 */
class BridgeManager private constructor() {
    private var initialized = false

    fun init() {
        if (initialized) return
        // JNI_OnLoad will be called from libbedrock_client.so
        nativeInit()
        initialized = true
    }

    fun attachActivity(activity: android.app.Activity) { nativeAttachActivity(activity) }
    fun detachActivity() { nativeDetachActivity() }
    fun callNative(method: String, args: Array<Any>): Any? = nativeCall(method, args)
    fun launchGame(instancePath: String, version: String): Int = nativeLaunchGame(instancePath, version)

    // Called from C++ -> Kotlin
    fun onNativeLog(level: Int, tag: String, msg: String) { com.bedrock.client.logger.Logger.nativeLog(level, tag, msg) }
    fun onModuleStateChanged(name: String, enabled: Boolean) { com.bedrock.client.modules.ModuleManager.getInstance().updateState(name, enabled) }

    // Native methods - implemented in cpp/bridge/
    private external fun nativeInit()
    private external fun nativeAttachActivity(activity: android.app.Activity)
    private external fun nativeDetachActivity()
    private external fun nativeCall(method: String, args: Array<Any>): Any?
    private external fun nativeLaunchGame(instancePath: String, version: String): Int

    companion object {
        @Volatile private var INSTANCE: BridgeManager? = null
        fun getInstance() = INSTANCE ?: synchronized(this) { INSTANCE ?: BridgeManager().also { INSTANCE = it } }
    }
}
