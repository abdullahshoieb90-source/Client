
package com.bedrock.client.modules.impl
import com.bedrock.client.modules.GameModule

class CPSCounterModule : GameModule("CPS Counter", "يعرض عدد الضغطات في الثانية (Clicks Per Second)", Category.RENDER, true) {
    private val leftClicks = mutableListOf<Long>()
    private val rightClicks = mutableListOf<Long>()

    fun onLeftClick() { leftClicks.add(System.currentTimeMillis()) }
    fun onRightClick() { rightClicks.add(System.currentTimeMillis()) }

    fun getLeftCPS(): Int {
        val now = System.currentTimeMillis()
        leftClicks.removeIf { now - it > 1000 }
        return leftClicks.size
    }
    fun getRightCPS(): Int {
        val now = System.currentTimeMillis()
        rightClicks.removeIf { now - it > 1000 }
        return rightClicks.size
    }

    override fun onEnable() { nativeSetEnabled(true) }
    override fun onDisable() { nativeSetEnabled(false) }
    private external fun nativeSetEnabled(e: Boolean)
}
