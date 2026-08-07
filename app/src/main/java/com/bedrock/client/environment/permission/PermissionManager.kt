package com.bedrock.client.environment.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.content.ContextCompat

class PermissionManager(private val context: Context) {

    fun hasStorage(): Boolean =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    fun hasInternet(): Boolean =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.INTERNET
        ) == PackageManager.PERMISSION_GRANTED

    fun hasAll(): List<String> = listOf(Manifest.permission.INTERNET)

    /**
     * Whether the app can write to the shared games/com.mojang folder that the
     * official Minecraft app reads from. On Android 11+ (API 30+) this requires
     * the "All files access" special permission, not the legacy runtime permissions.
     * Without it, MinecraftEnvironmentManager can only keep the prepared instance
     * local to this app's sandbox.
     */
    fun hasSharedStorageAccess(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            hasStorage()
        }
    }

    /**
     * Builds the Intent that sends the user to the system settings screen where
     * they can grant "All files access" to this app. Caller is responsible for
     * starting it (e.g. via startActivity or an Activity Result launcher) and for
     * re-checking hasSharedStorageAccess() in onResume, since there is no direct
     * runtime-permission callback for this special permission.
     */
    fun buildSharedStorageAccessIntent(): Intent {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Intent(
                Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                Uri.parse("package:${context.packageName}")
            )
        } else {
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:${context.packageName}")
            }
        }
    }
}
