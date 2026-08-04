package com.bedrock.client.environment.sandbox

import android.content.Context
import android.os.Environment
import com.bedrock.client.logger.Logger
import com.bedrock.client.minecraft.instance.InstanceManager
import com.bedrock.client.minecraft.profile.ProfileManager
import java.io.File

class MinecraftEnvironmentManager(private val context: Context) {
    private val sandboxManager = SandboxManager(context)
    private val syncManager = SandboxSyncManager()
    private val instanceManager = InstanceManager.getInstance(context)
    private val profileManager = ProfileManager(context)

    fun prepare(
        instanceId: String,
        syncToSharedStorage: Boolean = true
    ): PreparationReport {
        val instance = instanceManager.getInstance(instanceId)
        val profile = profileManager.ensureDefaultProfile(instanceId)
        val sandbox = sandboxManager.createSandbox(instanceId)
        val notes = syncManager
            .syncInstanceIntoSandbox(instance, sandbox, profile)
            .toMutableList()

        val exportBundleDir = File(instance.exportsDir, "active/games/com.mojang")
        syncManager.exportSandboxSnapshot(sandbox, exportBundleDir)

        var activeStorageDir: File? = null
        if (syncToSharedStorage) {
            val sharedTarget = resolveSharedMinecraftStorage()
            if (sharedTarget != null && syncManager.exportSandboxSnapshot(sandbox, sharedTarget)) {
                activeStorageDir = sharedTarget
                notes += "Mirrored active instance ${instance.id} to ${sharedTarget.absolutePath}."
            } else {
                notes += "Prepared private sandbox for ${instance.id}, but shared Minecraft storage is not writable on this device."
            }
        } else {
            notes += "Prepared private sandbox for ${instance.id} without shared-storage sync."
        }

        Logger.i(
            "Environment",
            "Prepared instance ${instance.id}; sharedTarget=${activeStorageDir?.absolutePath ?: "local-only"}"
        )

        return PreparationReport(
            instance = instance,
            sandbox = sandbox,
            exportBundleDir = exportBundleDir,
            activeStorageDir = activeStorageDir,
            notes = notes,
            synchronizedToSharedStorage = activeStorageDir != null
        )
    }

    @Suppress("DEPRECATION")
    private fun resolveSharedMinecraftStorage(): File? {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            return null
        }

        return runCatching {
            val root = Environment.getExternalStorageDirectory() ?: return null
            File(root, "games/com.mojang")
        }.getOrNull()
    }

    data class PreparationReport(
        val instance: InstanceManager.Instance,
        val sandbox: SandboxLayout,
        val exportBundleDir: File,
        val activeStorageDir: File?,
        val notes: List<String>,
        val synchronizedToSharedStorage: Boolean
    )
}
