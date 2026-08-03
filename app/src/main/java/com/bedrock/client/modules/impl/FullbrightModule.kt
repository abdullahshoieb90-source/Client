
package com.bedrock.client.modules.impl
import com.bedrock.client.modules.GameModule

class FullbrightModule : GameModule("Fullbright", "زيادة الإضاءة", Category.WORLD) {
    override fun onEnable() { nativeEnable() }
    override fun onDisable() { nativeDisable() }
    private external fun nativeEnable()
    private external fun nativeDisable()
}
