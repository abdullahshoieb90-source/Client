package com.bedrock.client.resourcepacks

import android.content.Context
import com.bedrock.client.minecraft.instance.InstanceManager
import java.io.File

class ResourcePackManager(private val context: Context) {
    private val instanceManager = InstanceManager.getInstance(context)

    fun getPacks(instanceId: String = DEFAULT_INSTANCE_ID): List<File> {
        val dir = instanceManager.getInstance(instanceId).resourcePacksDir
        dir.mkdirs()
        return dir.listFiles()?.sortedBy { it.name } ?: emptyList()
    }

    fun install(pack: File, instanceId: String = DEFAULT_INSTANCE_ID) {
        val dir = instanceManager.getInstance(instanceId).resourcePacksDir
        dir.mkdirs()
        pack.copyTo(File(dir, pack.name), overwrite = true)
    }

    companion object {
        private const val DEFAULT_INSTANCE_ID = "default"
    }
}
