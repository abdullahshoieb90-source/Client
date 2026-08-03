
package com.bedrock.client.worlds
import android.content.Context
import java.io.File

data class WorldInfo(val id: String, val name: String, val path: File, val size: Long)

class WorldManager private constructor(private val context: Context) {
    fun loadWorlds(): List<WorldInfo> {
        val dir = File(context.filesDir, "minecraftWorlds")
        dir.mkdirs()
        return dir.listFiles { f -> f.isDirectory }?.map { WorldInfo(it.name, it.name, it, it.length()) } ?: emptyList()
    }

    companion object {
        @Volatile private var INSTANCE: WorldManager? = null
        fun getInstance(ctx: Context) = INSTANCE ?: synchronized(this) { INSTANCE ?: WorldManager(ctx).also { INSTANCE = it } }
    }
}
