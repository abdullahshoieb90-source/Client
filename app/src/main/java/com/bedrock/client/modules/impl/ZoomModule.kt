
package com.bedrock.client.modules.impl
import com.bedrock.client.modules.GameModule

class ZoomModule : GameModule("Zoom", "تكبير الرؤية", Category.RENDER) {
    var zoomLevel = 3.0f
    override fun onEnable() { nativeEnable(zoomLevel) }
    override fun onDisable() { nativeDisable() }
    private external fun nativeEnable(level: Float)
    private external fun nativeDisable()
}
