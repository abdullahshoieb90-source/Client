
package com.bedrock.client.runtime
import android.content.Context
import com.bedrock.client.logger.Logger

class GameRuntime(private val context: Context) {
    fun onCreate() { Logger.i("GameRuntime", "Runtime created") }
    fun onDestroy() { Logger.i("GameRuntime", "Runtime destroyed") }
    fun onTick() { /* called from native */ }
    fun onRender(delta: Float) { /* overlay render */ }
}
