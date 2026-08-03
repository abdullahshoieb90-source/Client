
package com.bedrock.client.shaders
import android.content.Context
import java.io.File

class ShaderManager(private val context: Context) {
    fun getShaders(): List<File> = File(context.filesDir, "shaders").listFiles()?.toList() ?: emptyList()
    fun applyShader(file: File) { /* copy to renderer path */ }
}
