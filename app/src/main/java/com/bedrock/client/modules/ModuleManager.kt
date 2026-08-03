
package com.bedrock.client.modules

import com.bedrock.client.modules.impl.*

class ModuleManager private constructor() {
    private val modules = mutableListOf<GameModule>()

    init {
        // كل المودات
        register(FPSCounterModule())
        register(CPSCounterModule())
        register(ZoomModule())
        register(FullbrightModule())
        register(ESPModule())
        register(HitboxModule())
        register(AutoSprintModule())
        register(KeystrokesModule())
        register(CoordinatesModule())
        register(TimeChangerModule())
    }

    fun register(m: GameModule) { modules.add(m) }
    fun getAllModules(): List<GameModule> = modules
    fun getModule(name: String): GameModule? = modules.find { it.name.equals(name, ignoreCase = true) }
    fun getByCategory(cat: GameModule.Category): List<GameModule> = modules.filter { it.category == cat }

    fun onGameStarted() { modules.filter { it.isEnabled }.forEach { it.onEnable() } }
    fun onGameStopped() { modules.filter { it.isEnabled }.forEach { it.onDisable() } }
    fun onTick() { modules.filter { it.isEnabled }.forEach { it.onTick() } }
    fun onRender(delta: Float) { modules.filter { it.isEnabled }.forEach { it.onRender(delta) } }

    fun updateState(name: String, enabled: Boolean) { getModule(name)?.isEnabled = enabled }
    fun notifyStateChanged(m: GameModule) { /* JNI sync to C++ */ nativeNotifyModuleState(m.name, m.isEnabled) }

    private external fun nativeNotifyModuleState(name: String, enabled: Boolean)

    companion object {
        @Volatile private var INSTANCE: ModuleManager? = null
        fun getInstance() = INSTANCE ?: synchronized(this) { INSTANCE ?: ModuleManager().also { INSTANCE = it } }
    }
}
