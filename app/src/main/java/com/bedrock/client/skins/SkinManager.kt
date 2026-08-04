package com.bedrock.client.skins

import android.content.Context
import com.bedrock.client.minecraft.instance.InstanceManager
import java.io.File

data class SkinInfo(val id: String, val name: String, val path: File)

class SkinManager(private val context: Context) {
    private val instanceManager = InstanceManager.getInstance(context)

    fun getSkins(instanceId: String = DEFAULT_INSTANCE_ID): List<SkinInfo> {
        val dir = instanceManager.getInstance(instanceId).skinPacksDir
        dir.mkdirs()
        return dir.listFiles()
            ?.sortedBy { it.name }
            ?.map { SkinInfo(it.nameWithoutExtension, it.nameWithoutExtension, it) }
            ?: emptyList()
    }

    fun install(skinFile: File, instanceId: String = DEFAULT_INSTANCE_ID) {
        val dir = instanceManager.getInstance(instanceId).skinPacksDir
        dir.mkdirs()
        skinFile.copyTo(File(dir, skinFile.name), overwrite = true)
    }

    companion object {
        private const val DEFAULT_INSTANCE_ID = "default"
    }
}
