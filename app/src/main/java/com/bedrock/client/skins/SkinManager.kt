
package com.bedrock.client.skins
import android.content.Context
import java.io.File

data class SkinInfo(val id: String, val name: String, val path: File)

class SkinManager(private val context: Context) {
    fun getSkins(): List<SkinInfo> {
        val dir = File(context.filesDir, "skins")
        dir.mkdirs()
        return dir.listFiles()?.map { SkinInfo(it.nameWithoutExtension, it.nameWithoutExtension, it) } ?: emptyList()
    }
}
