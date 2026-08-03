
package com.bedrock.client.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bedrock.client.bridge.BridgeManager
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
    }

    override fun onDestroy() {
        runtime.onDestroy()
        ModuleManager.getInstance().onGameStopped()
        BridgeManager.getInstance().detachActivity()
        super.onDestroy()
    }
}
