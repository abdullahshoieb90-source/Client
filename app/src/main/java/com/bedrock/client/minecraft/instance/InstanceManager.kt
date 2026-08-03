package com.bedrock.client.minecraft.instance

import android.content.Context
import com.bedrock.client.minecraft.version.VersionManager
import java.io.File

class InstanceManager private constructor(private val context: Context) {
    data class Instance(val id: String, val path: File, val version: String)

    private val versionManager = VersionManager.getInstance(context)

    fun getInstance(id: String): Instance {
        val dir = File(context.filesDir, "instances/$id")
        dir.mkdirs()
        return Instance(id, dir, detectedVersion())
    }

    fun listInstances(): List<Instance> {
        val root = File(context.filesDir, "instances")
        val version = detectedVersion()
        return root.listFiles()?.map { Instance(it.name, it, version) } ?: emptyList()
    }

    private fun detectedVersion(): String =
        versionManager.getActiveVersion()?.code ?: VERSION_NOT_INSTALLED

    companion object {
        private const val VERSION_NOT_INSTALLED = "not-installed"

        @Volatile
        private var INSTANCE: InstanceManager? = null

        fun getInstance(context: Context): InstanceManager =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: InstanceManager(context.applicationContext).also { INSTANCE = it }
            }
    }
}
