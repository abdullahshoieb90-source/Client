
package com.bedrock.client.loader.library
import java.io.File

class LibraryLoader {
    fun loadLibraries(dir: File) {
        dir.listFiles { f -> f.extension == "so" }?.forEach { System.load(it.absolutePath) }
    }
}
