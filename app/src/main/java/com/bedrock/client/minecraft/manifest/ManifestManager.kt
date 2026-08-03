
package com.bedrock.client.minecraft.manifest
import android.content.Context
import com.google.gson.Gson
import java.io.File

class ManifestManager(private val context: Context) {
    data class Manifest(val version: String, val modules: List<String>)
    fun parse(file: File): Manifest = Gson().fromJson(file.readText(), Manifest::class.java)
    fun parseAndroidManifest(): android.content.pm.PackageInfo? = null
}
