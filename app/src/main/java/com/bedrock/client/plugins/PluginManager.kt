
package com.bedrock.client.plugins
import java.io.File

class PluginManager {
    data class Plugin(val id: String, val name: String, val version: String, val file: File, var enabled: Boolean)

    private val plugins = mutableListOf<Plugin>()

    fun loadPlugins(dir: File) {
        if (!dir.exists()) return
        dir.listFiles { f -> f.extension == "jar" || f.extension == "dex" }?.forEach { f ->
            plugins.add(Plugin(f.nameWithoutExtension, f.nameWithoutExtension, "1.0", f, true))
        }
    }

    fun getAll(): List<Plugin> = plugins
    fun enable(id: String) { plugins.find { it.id == id }?.enabled = true }
    fun disable(id: String) { plugins.find { it.id == id }?.enabled = false }
}
