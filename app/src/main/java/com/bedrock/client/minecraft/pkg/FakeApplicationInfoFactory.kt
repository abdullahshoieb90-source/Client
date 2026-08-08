Enterpackage com.bedrock.client.minecraft.pkg

import android.content.pm.ApplicationInfo
import com.bedrock.client.minecraft.instance.InstanceManager

/**
 * Counterpart to [com.bedrock.client.minecraft.`package`.MinecraftPackageManager] for the
 * install-free flow: that class reads a real ApplicationInfo from PackageManager for an
 * *installed* Minecraft. This factory builds an equivalent-shaped ApplicationInfo by hand
 * for an *imported, never-installed* apk, so the rest of the pipeline (native loader,
 * runtime bootstrap) can consume either one through the same fields.
 *
 * This only fabricates the plain data fields PackageManager itself would have exposed
 * (paths, package name, data dir) — it does not load or execute anything belonging to
 * Minecraft.
 */
object FakeApplicationInfoFactory {

    const val MINECRAFT_PACKAGE = "com.mojang.minecraftpe"

    fun build(instance: InstanceManager.Instance): ApplicationInfo? {
        val apk = instance.importedBaseApk ?: return null

        return ApplicationInfo().apply {
            packageName = MINECRAFT_PACKAGE
            sourceDir = apk.absolutePath
            publicSourceDir = apk.absolutePath
            nativeLibraryDir = instance.libsDir.absolutePath
            dataDir = instance.profileDir.absolutePath
            // Keep splitSourceDirs null here — wire it up once split-apk import is added
            // to ApkImportManager (base.apk + config.<abi>.apk + config.<lang>.apk).
        }
    }
}
