package com.bedrock.client.worlds

import android.content.Context
import com.bedrock.client.minecraft.instance.InstanceManager
import java.io.File

data class WorldInfo(val id: String, val name: String, val path: File, val size: Long)

class WorldManager private constructor(private val context: Context) {
    private val instanceManager = InstanceManager.getInstance(context)

    fun loadWorlds(instanceId: String = DEFAULT_INSTANCE_ID): List<WorldInfo> {
        val dir = instanceManager.getInstance(instanceId).worldsDir
        dir.mkdirs()
        return dir.listFiles { file -> file.isDirectory }
            ?.sortedBy { it.name }
            ?.map { WorldInfo(it.name, it.name, it, it.length()) }
            ?: emptyList()
    }

    companion object {
        private const val DEFAULT_INSTANCE_ID = "default"

        @Volatile
        private var INSTANCE: WorldManager? = null
        fun getInstance(ctx: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: WorldManager(ctx.applicationContext).also { INSTANCE = it }
            }
    }
}
