package com.bedrock.client.environment.sandbox

import android.content.Context
import java.io.File

class SandboxManager(private val context: Context) {

    fun createSandbox(instanceId: String): SandboxLayout {
        val root = File(context.filesDir, "sandbox/$instanceId")
        val gamesDir = File(root, "games")
        val comMojangDir = File(gamesDir, "com.mojang")
        val minecraftPeDir = File(comMojangDir, "minecraftpe")
        val worldsDir = File(comMojangDir, "minecraftWorlds")
        val resourcePacksDir = File(comMojangDir, "resource_packs")
        val behaviorPacksDir = File(comMojangDir, "behavior_packs")
        val skinPacksDir = File(comMojangDir, "skin_packs")
        val developmentResourcePacksDir = File(comMojangDir, "development_resource_packs")
        val developmentBehaviorPacksDir = File(comMojangDir, "development_behavior_packs")
        val logsDir = File(root, "logs")
        val metadataDir = File(root, "metadata")
        val optionsFile = File(minecraftPeDir, "options.txt")
        val globalResourcePacksFile = File(minecraftPeDir, "global_resource_packs.json")
        val validKnownPacksFile = File(minecraftPeDir, "valid_known_packs.json")
        val instanceStateFile = File(metadataDir, "instance-state.txt")

        val layout = SandboxLayout(
            root = root,
            gamesDir = gamesDir,
            comMojangDir = comMojangDir,
            minecraftPeDir = minecraftPeDir,
            worldsDir = worldsDir,
            resourcePacksDir = resourcePacksDir,
            behaviorPacksDir = behaviorPacksDir,
            skinPacksDir = skinPacksDir,
            developmentResourcePacksDir = developmentResourcePacksDir,
            developmentBehaviorPacksDir = developmentBehaviorPacksDir,
            logsDir = logsDir,
            metadataDir = metadataDir,
            optionsFile = optionsFile,
            globalResourcePacksFile = globalResourcePacksFile,
            validKnownPacksFile = validKnownPacksFile,
            instanceStateFile = instanceStateFile
        )

        ensureLayout(layout, instanceId)
        return layout
    }

    fun getSandboxPath(instanceId: String): File = File(context.filesDir, "sandbox/$instanceId")

    private fun ensureLayout(layout: SandboxLayout, instanceId: String) {
        listOf(
            layout.root,
            layout.gamesDir,
            layout.comMojangDir,
            layout.minecraftPeDir,
            layout.worldsDir,
            layout.resourcePacksDir,
            layout.behaviorPacksDir,
            layout.skinPacksDir,
            layout.developmentResourcePacksDir,
            layout.developmentBehaviorPacksDir,
            layout.logsDir,
            layout.metadataDir
        ).forEach { it.mkdirs() }

        writeIfAbsent(
            layout.optionsFile,
            buildString {
                appendLine("gfx_viewdistance:96")
                appendLine("ctrl_invertmouse:false")
                appendLine("last_instance:$instanceId")
            }
        )
        writeIfAbsent(layout.globalResourcePacksFile, "[]\n")
        writeIfAbsent(layout.validKnownPacksFile, "{\n  \"knownPacks\": []\n}\n")
        writeIfAbsent(
            layout.instanceStateFile,
            buildString {
                appendLine("instanceId=$instanceId")
                appendLine("status=created")
            }
        )
    }

    private fun writeIfAbsent(file: File, content: String) {
        if (file.exists()) {
            return
        }
        file.parentFile?.mkdirs()
        file.writeText(content)
    }
}
