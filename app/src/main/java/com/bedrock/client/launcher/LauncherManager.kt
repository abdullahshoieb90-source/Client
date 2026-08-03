
package com.bedrock.client.launcher

import android.content.Context
import com.bedrock.client.logger.Logger

class LauncherManager private constructor() {
    private var initialized = false
    private lateinit var launcher: Launcher

    fun initialize(context: Context) {
        if (initialized) return
        launcher = Launcher(context.applicationContext)
        initialized = true
        Logger.i("LauncherManager", "Initialized")
    }

    fun getLauncher(): Launcher {
        check(initialized) { "LauncherManager not initialized" }
        return launcher
    }

    companion object {
        @Volatile private var INSTANCE: LauncherManager? = null
        fun getInstance(): LauncherManager = INSTANCE ?: synchronized(this) {
            INSTANCE ?: LauncherManager().also { INSTANCE = it }
        }
    }
}
