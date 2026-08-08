
package com.bedrock.client.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bedrock.client.bridge.BridgeManager
import com.bedrock.client.minecraft.instance.InstanceManager
import com.bedrock.client.modules.ModuleManager
import com.bedrock.client.runtime.GameRuntime

class GameActivity : AppCompatActivity() {
    private lateinit var runtime: GameRuntime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Fullscreen immersive
        window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or android.view.View.SYSTEM_UI_FLAG_FULLSCREEN or android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        runtime = GameRuntime(this)
        runtime.onCreate()
        BridgeManager.getInstance().attachActivity(this)
        ModuleManager.getInstance().onGameStarted()

        val instanceId = intent.getStringExtra(EXTRA_INSTANCE_ID)
        if (instanceId != null) {
            // RuntimeManager.prepareImported() must already have been called by
            // Launcher.launchImported() before this Activity was started, so RuntimeConfig
            // on the native side already points at this instance's isolated apk/libs/data.
            val instance = InstanceManager.getInstance(this).getInstance(instanceId)
            val pid = BridgeManager.getInstance().launchGame(instance.path.absolutePath, instance.version)
            if (pid < 0) {
                // See cpp/bootstrap/bootstrap.cpp + cpp/runtime/MinecraftLoader.cpp:
                // MinecraftLoader::launch() still has to resolve and call Minecraft's real
                // native entry point — that part is intentionally left as a TODO (see
                // MinecraftLoader.cpp comment) rather than guessed.
                com.bedrock.client.logger.Logger.e("GameActivity", "launchGame failed for $instanceId")
            }
        }
    }

    override fun onDestroy() {
        runtime.onDestroy()
        ModuleManager.getInstance().onGameStopped()
        BridgeManager.getInstance().detachActivity()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_INSTANCE_ID = "com.bedrock.client.extra.INSTANCE_ID"
    }
}
