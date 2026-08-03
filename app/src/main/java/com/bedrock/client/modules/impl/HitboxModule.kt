
package com.bedrock.client.modules.impl
import com.bedrock.client.modules.GameModule
class HitboxModule : GameModule("Hitbox", "توسيع صندوق الاصطدام", Category.COMBAT) {
    var size = 0.5f
    override fun onEnable() { nativeEnable(size) }
    override fun onDisable() { nativeDisable() }
    private external fun nativeEnable(size: Float)
    private external fun nativeDisable()
}
