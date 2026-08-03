
package com.bedrock.client.loader.dex
import android.content.Context
import dalvik.system.DexClassLoader
import java.io.File

class DexLoader(private val context: Context) {
    fun loadDex(dexFile: File): ClassLoader {
        val optimizedDir = File(context.filesDir, "dex_opt")
        optimizedDir.mkdirs()
        return DexClassLoader(dexFile.absolutePath, optimizedDir.absolutePath, null, context.classLoader)
    }
    fun loadJar(jarFile: File): ClassLoader = loadDex(jarFile)
}
