
package com.bedrock.client.resourcepacks
import android.content.Context
import java.io.File

class ResourcePackManager(private val context: Context) {
    fun getPacks(): List<File> {
        val dir = File(context.filesDir, "resource_packs")
        return dir.listFiles()?.toList() ?: emptyList()
    }
    fun install(pack: File) { pack.copyTo(File(context.filesDir, "resource_packs/${pack.name}"), overwrite = true) }
}
