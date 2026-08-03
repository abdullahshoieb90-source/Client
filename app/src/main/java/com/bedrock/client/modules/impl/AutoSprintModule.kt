
package com.bedrock.client.modules.impl
import com.bedrock.client.modules.GameModule
class AutoSprintModule : GameModule("AutoSprint", "ركض تلقائي", Category.MOVEMENT) {
    override fun onEnable() { nativeEnable() }
    override fun onDisable() { nativeDisable() }
    private external fun nativeEnable()
    private external fun nativeDisable()
}
