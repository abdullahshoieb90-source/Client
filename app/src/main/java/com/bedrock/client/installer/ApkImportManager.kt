package com.bedrock.client.installer

import android.content.Context
import android.net.Uri
import android.os.Build
import com.bedrock.client.extractor.Extractor
import com.bedrock.client.logger.Logger
import com.bedrock.client.minecraft.instance.InstanceManager
import java.io.File
import java.util.zip.ZipFile

/**
 * Install-free apk import: copies a Minecraft apk the user already owns (picked via SAF,
 * so no storage-wide permission is required) into that instance's own [Instance.apkDir],
 * then extracts only this instance's native libs into [Instance.libsDir].
 *
 * Nothing here touches PackageManager or android.content.pm.PackageInstaller — the apk is
 * never registered as an installed app, so two instances can hold two different Minecraft
 * versions/apk copies side by side with zero collision.
 */
class ApkImportManager(private val context: Context) {

    private val extractor = Extractor()
    private val instanceManager = InstanceManager.getInstance(context)

    data class ImportResult(
        val instanceId: String,
        val apkFile: File,
        val versionName: String?,
        val versionCode: Long,
        val libsDir: File,
        val extractedLibCount: Int
    )

    /**
     * @param sourceApk content:// Uri the user picked (e.g. from a SAF file picker) for a
     *   base.apk they exported from their own licensed Minecraft install.
     */
    fun importApk(instanceId: String, sourceApk: Uri): Result<ImportResult> = runCatching {
        val instance = instanceManager.getInstance(instanceId)
        val destApk = File(instance.apkDir, "base.apk")

        context.contentResolver.openInputStream(sourceApk)?.use { input ->
            destApk.outputStream().use { output -> input.copyTo(output) }
        } ?: error("Unable to open the selected apk")

        val (versionName, versionCode) = readManifestVersion(destApk)
        val abi = Build.SUPPORTED_ABIS.firstOrNull() ?: "arm64-v8a"

        instance.libsDir.listFiles()?.forEach { it.delete() } // clear any previous version's libs
        extractor.extractApk(destApk, instance.libsDir, abi)
        val libCount = instance.libsDir.listFiles { f -> f.extension == "so" }?.size ?: 0

        if (libCount == 0) {
            Logger.e("ApkImportManager", "No native libs found for abi=$abi in $destApk")
        }

        ImportResult(
            instanceId = instanceId,
            apkFile = destApk,
            versionName = versionName,
            versionCode = versionCode,
            libsDir = instance.libsDir,
            extractedLibCount = libCount
        )
    }.onFailure { error ->
        Logger.e("ApkImportManager", "Import failed for instance $instanceId", error)
    }

    fun removeImport(instanceId: String) {
        val instance = instanceManager.getInstance(instanceId)
        instance.apkDir.listFiles()?.forEach { it.delete() }
        instance.libsDir.listFiles()?.forEach { it.delete() }
    }

    /**
     * Reads versionName/versionCode straight out of AndroidManifest.xml's binary XML
     * without needing the apk to be installed. Best-effort: returns nulls if parsing
     * fails, launch-time compatibility checks should not hard-depend on this.
     */
    private fun readManifestVersion(apk: File): Pair<String?, Long> {
        return try {
            ZipFile(apk).use { zip ->
                val entry = zip.getEntry("AndroidManifest.xml") ?: return null to 0L
                // Full binary-XML parsing is out of scope here; PackageManager can still
                // parse an *uninstalled* apk's manifest via PackageManager#getPackageArchiveInfo,
                // which is the simpler, more reliable path — see getArchiveInfo() below.
                zip.getInputStream(entry).close()
                getArchiveInfo(apk)
            }
        } catch (e: Exception) {
            Logger.e("ApkImportManager", "Failed reading manifest from $apk", e)
            null to 0L
        }
    }

    @Suppress("DEPRECATION")
    private fun getArchiveInfo(apk: File): Pair<String?, Long> {
        val pm = context.packageManager
        val info = pm.getPackageArchiveInfo(apk.absolutePath, 0) ?: return null to 0L
        val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            info.longVersionCode
        } else {
            info.versionCode.toLong()
        }
        return info.versionName to versionCode
    }
}

