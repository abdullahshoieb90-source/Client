
package com.bedrock.client.modules.impl
import com.bedrock.client.modules.GameModule
class ESPModule : GameModule("ESP", "رؤية الكيانات من خلال الجدران", Category.RENDER) {
    override fun onEnable() { nativeEnable() }
    override fun onDisable() { nativeDisable() }
    private external fun nativeEnable()
    private external fun nativeDisable()
}
