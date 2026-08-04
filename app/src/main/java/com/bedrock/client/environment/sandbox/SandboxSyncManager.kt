package com.bedrock.client.environment.sandbox

import com.bedrock.client.logger.Logger
import com.bedrock.client.minecraft.instance.InstanceManager
import com.bedrock.client.minecraft.profile.ProfileEntity
import java.io.File

class SandboxSyncManager {

    fun syncInstanceIntoSandbox(
        instance: InstanceManager.Instance,
        layout: SandboxLayout,
        profile: ProfileEntity?
    ): List<String> {
        val notes = mutableListOf<String>()

        mirrorDirectory(instance.worldsDir, layout.worldsDir)
        mirrorDirectory(instance.resourcePacksDir, layout.resourcePacksDir)
        mirrorDirectory(instance.behaviorPacksDir, layout.behaviorPacksDir)
        mirrorDirectory(instance.skinPacksDir, layout.skinPacksDir)
        mirrorDirectory(instance.resourcePacksDir, layout.developmentResourcePacksDir)
        mirrorDirectory(instance.behaviorPacksDir, layout.developmentBehaviorPacksDir)

        val optionsSource = File(instance.profileDir, "options.txt")
        if (optionsSource.exists()) {
            copyFile(optionsSource, layout.optionsFile)
        } else {
            notes += "options.txt was missing for instance ${instance.id}; sandbox defaults were kept."
        }

        val profileName = profile?.name?.takeIf { it.isNotBlank() } ?: "Player"
        layout.instanceStateFile.writeText(
            buildString {
                appendLine("instanceId=${instance.id}")
                appendLine("version=${instance.version}")
                appendLine("profileName=$profileName")
                appendLine("worldCount=${instance.worldsDir.listFiles()?.count { it.isDirectory } ?: 0}")
                appendLine("resourcePackCount=${instance.resourcePacksDir.listFiles()?.size ?: 0}")
                appendLine("behaviorPackCount=${instance.behaviorPacksDir.listFiles()?.size ?: 0}")
                appendLine("skinPackCount=${instance.skinPacksDir.listFiles()?.size ?: 0}")
            }
        )

        Logger.i(
            "SandboxSync",
            "Sandbox prepared for ${instance.id} at ${layout.root.absolutePath}"
        )

        return notes
    }

    fun exportSandboxSnapshot(layout: SandboxLayout, targetComMojangDir: File): Boolean {
        return runCatching {
            mirrorDirectory(layout.comMojangDir, targetComMojangDir)
            true
        }.getOrElse { error ->
            Logger.e(
                "SandboxSync",
                "Failed to export sandbox to ${targetComMojangDir.absolutePath}",
                error
            )
            false
        }
    }

    private fun mirrorDirectory(source: File, target: File) {
        if (target.exists()) {
            target.deleteRecursively()
        }

        if (!source.exists()) {
            target.mkdirs()
            return
        }

        source.copyRecursively(target, overwrite = true)
    }

    private fun copyFile(source: File, target: File) {
        target.parentFile?.mkdirs()
        source.copyTo(target, overwrite = true)
    }
}
