
package com.bedrock.client.launcher

import android.app.Application
import com.bedrock.client.bootstrap.Bootstrap
import com.bedrock.client.logger.Logger
import com.bedrock.client.crash.CrashHandler
import com.bedrock.client.settings.SettingsManager
import com.bedrock.client.environment.workspace.WorkspaceManager
import com.bedrock.client.security.SecurityManager
import timber.log.Timber

class ClientApplication : Application() {
    companion object {
        lateinit var instance: ClientApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // تسلسل التشغيل: Application -> Bootstrap -> Launcher
        CrashHandler.init(this)
        Logger.init(this)
        Timber.i("ClientApplication starting...")

        SecurityManager.getInstance(this).performChecks()
        SettingsManager.getInstance(this).load()
        WorkspaceManager.getInstance(this).prepare()

        Bootstrap.getInstance(this).initialize {
            LauncherManager.getInstance().initialize(this)
        }
    }
}
