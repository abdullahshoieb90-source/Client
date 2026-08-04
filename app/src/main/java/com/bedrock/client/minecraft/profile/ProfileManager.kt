package com.bedrock.client.minecraft.profile

import android.content.Context
import com.bedrock.client.logger.Logger
import com.bedrock.client.minecraft.instance.InstanceManager
import java.io.File

class ProfileManager(private val context: Context) {
    private val instanceManager = InstanceManager.getInstance(context)

    fun loadProfile(id: String): ProfileEntity? {
        return runCatching {
            val instance = instanceManager.getInstance(id)
            val nameFile = File(instance.profileDir, "name.txt")
            val optionsFile = File(instance.profileDir, "options.txt")

            if (!nameFile.exists()) {
                nameFile.writeText("Player")
            }
            if (!optionsFile.exists()) {
                optionsFile.writeText(defaultOptions(id))
            }

            ProfileEntity(
                id = id,
                name = nameFile.readText().trim().ifBlank { "Player" },
                options = optionsFile.readText()
            )
        }.getOrElse { error ->
            Logger.e("ProfileManager", "Failed to load profile $id", error)
            null
        }
    }

    fun ensureDefaultProfile(instanceId: String = DEFAULT_INSTANCE_ID): ProfileEntity {
        return loadProfile(instanceId)
            ?: ProfileEntity(instanceId, "Player", defaultOptions(instanceId))
    }

    fun saveProfile(profile: ProfileEntity) {
        val instance = instanceManager.getInstance(profile.id)
        File(instance.profileDir, "name.txt").writeText(profile.name.ifBlank { "Player" })
        File(instance.profileDir, "options.txt").writeText(
            profile.options.ifBlank { defaultOptions(profile.id) }
        )
    }

    fun getOptionsFile(instanceId: String = DEFAULT_INSTANCE_ID): File =
        File(instanceManager.getInstance(instanceId).profileDir, "options.txt")

    private fun defaultOptions(instanceId: String): String = buildString {
        appendLine("gfx_viewdistance:96")
        appendLine("ctrl_invertmouse:false")
        appendLine("last_instance:$instanceId")
    }

    companion object {
        private const val DEFAULT_INSTANCE_ID = "default"
    }
}
