
package com.bedrock.client.environment.permission
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionManager(private val context: Context) {
    fun hasStorage(): Boolean = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    fun hasInternet(): Boolean = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
    fun hasAll(): List<String> = listOf(Manifest.permission.INTERNET)
}
