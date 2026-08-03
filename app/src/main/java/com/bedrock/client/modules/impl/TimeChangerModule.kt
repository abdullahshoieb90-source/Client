
package com.bedrock.client.modules.impl
import com.bedrock.client.modules.GameModule
class TimeChangerModule : GameModule("Time Changer", "تغيير وقت العالم", Category.WORLD) {
    var time = 6000L
    override fun onEnable() { nativeSetTime(time) }
    override fun onDisable() { nativeResetTime() }
    private external fun nativeSetTime(t: Long)
    private external fun nativeResetTime()
}
