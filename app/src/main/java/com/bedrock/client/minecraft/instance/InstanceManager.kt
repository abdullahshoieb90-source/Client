
package com.bedrock.client.minecraft.instance
import android.content.Context
import java.io.File

class InstanceManager private constructor(private val context: Context) {
    data class Instance(val id: String, val path: File, val version: String)

    fun getInstance(id: String): Instance {
        val dir = File(context.filesDir, "instances/$id")
        dir.mkdirs()
        return Instance(id, dir, "1.21.100")
    }

    fun listInstances(): List<Instance> {
        val root = File(context.filesDir, "instances")
        return root.listFiles()?.map { Instance(it.name, it, "1.21.100") } ?: emptyList()
    }

    companion object {
        @Volatile private var INSTANCE: InstanceManager? = null
        fun getInstance(ctx: Context) = INSTANCE ?: synchronized(this) { INSTANCE ?: InstanceManager(ctx).also { INSTANCE = it } }
    }
}
