
package com.bedrock.client.minecraft.`package`
import android.content.pm.PackageManager
import android.content.Context

class MinecraftPackageManager(private val context: Context) {
    fun isInstalled(): Boolean {
        return try { context.packageManager.getPackageInfo("com.mojang.minecraftpe", 0); true } catch (e: PackageManager.NameNotFoundException) { false }
    }
    fun getVersion(): String? {
        return try { context.packageManager.getPackageInfo("com.mojang.minecraftpe", 0).versionName } catch (e: Exception) { null }
    }
}
