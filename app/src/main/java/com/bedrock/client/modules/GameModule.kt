
package com.bedrock.client.modules

abstract class GameModule(
    val name: String,
    val description: String,
    val category: Category,
    var isEnabled: Boolean = false
) {
    enum class Category { RENDER, COMBAT, MOVEMENT, WORLD, CLIENT }

    abstract fun onEnable()
    abstract fun onDisable()
    open fun onTick() {}
    open fun onRender(delta: Float) {}

    // Note: cannot be named setEnabled() because the `isEnabled` property
    // already generates a setEnabled(Boolean) setter on the JVM.
    fun setModuleEnabled(enabled: Boolean) {
        if (isEnabled == enabled) return
        isEnabled = enabled
        if (enabled) onEnable() else onDisable()
        ModuleManager.getInstance().notifyStateChanged(this)
    }
}
