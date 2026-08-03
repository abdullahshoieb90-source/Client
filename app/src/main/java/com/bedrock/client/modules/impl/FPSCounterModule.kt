
package com.bedrock.client.modules.impl
import com.bedrock.client.modules.GameModule

class FPSCounterModule : GameModule("FPS Counter", "يعرض عدد الإطارات في الثانية", Category.RENDER, true) {
    private var fps = 0
    private var lastTime = System.nanoTime()
    private var frames = 0

    fun calculateFPS(): Int {
        frames++
        val now = System.nanoTime()
        val elapsed = (now - lastTime) / 1_000_000_000.0
        if (elapsed >= 1.0) {
            fps = (frames / elapsed).toInt()
            frames = 0
            lastTime = now
        }
        return fps
    }

    override fun onEnable() { nativeSetEnabled(true) }
    override fun onDisable() { nativeSetEnabled(false) }
    override fun onRender(delta: Float) { calculateFPS() }
    private external fun nativeSetEnabled(e: Boolean)
}
