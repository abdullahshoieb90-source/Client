
package com.bedrock.client.hook

class HookManager private constructor() {
    fun installHook(name: String): Boolean {
        return nativeInstallHook(name)
    }
    fun removeHook(name: String): Boolean = nativeRemoveHook(name)

    private external fun nativeInstallHook(name: String): Boolean
    private external fun nativeRemoveHook(name: String): Boolean

    companion object {
        @Volatile private var INSTANCE: HookManager? = null
        fun getInstance() = INSTANCE ?: synchronized(this) { INSTANCE ?: HookManager().also { INSTANCE = it } }
    }
}
