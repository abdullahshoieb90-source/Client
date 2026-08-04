package com.bedrock.client.minecraft.instance

import android.content.Context
import com.bedrock.client.minecraft.version.VersionManager
import java.io.File

class InstanceManager private constructor(private val context: Context) {
    data class Instance(
        val id: String,
        val path: File,
        val version: String,
        val profileDir: File,
        val worldsDir: File,
        val resourcePacksDir: File,
        val behaviorPacksDir: File,
        val skinPacksDir: File,
        val shadersDir: File,
        val exportsDir: File,
        val cacheDir: File
    )

    private val versionManager = VersionManager.getInstance(context)

    fun getInstance(id: String): Instance {
        val root = File(context.filesDir, "instances/$id")
        return createInstance(root)
    }

    fun listInstances(): List<Instance> {
        val root = File(context.filesDir, "instances")
        root.mkdirs()
        return root.listFiles { file -> file.isDirectory }
            ?.sortedBy { it.name }
            ?.map(::createInstance)
            ?: emptyList()
    }

    private fun createInstance(root: File): Instance {
        root.mkdirs()

        val profileDir = File(root, "profile").apply { mkdirs() }
        val worldsDir = File(root, "minecraftWorlds").apply { mkdirs() }
        val resourcePacksDir = File(root, "resource_packs").apply { mkdirs() }
        val behaviorPacksDir = File(root, "behavior_packs").apply { mkdirs() }
        val skinPacksDir = File(root, "skin_packs").apply { mkdirs() }
        val shadersDir = File(root, "shaders").apply { mkdirs() }
        val exportsDir = File(root, "exports").apply { mkdirs() }
        val cacheDir = File(root, "cache").apply { mkdirs() }

        val optionsFile = File(profileDir, "options.txt")
        if (!optionsFile.exists()) {
            optionsFile.writeText(defaultOptions(root.name))
        }

        val profileNameFile = File(profileDir, "name.txt")
        if (!profileNameFile.exists()) {
            profileNameFile.writeText("Player")
        }

        return Instance(
            id = root.name,
            path = root,
            version = detectedVersion(),
            profileDir = profileDir,
            worldsDir = worldsDir,
            resourcePacksDir = resourcePacksDir,
            behaviorPacksDir = behaviorPacksDir,
            skinPacksDir = skinPacksDir,
            shadersDir = shadersDir,
            exportsDir = exportsDir,
            cacheDir = cacheDir
        )
    }

    private fun detectedVersion(): String =
        versionManager.getActiveVersion()?.code ?: VERSION_NOT_INSTALLED

    private fun defaultOptions(instanceId: String): String = buildString {
        appendLine("gfx_viewdistance:96")
        appendLine("ctrl_invertmouse:false")
        appendLine("last_instance:$instanceId")
    }

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
