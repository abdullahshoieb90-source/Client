
package com.bedrock.client.installer
import java.io.File

class Installer {
    fun installMod(file: File, target: File) { file.copyTo(target, overwrite = true) }
    fun installPack(file: File, targetDir: File) { /* copy to resource_packs */ }
    fun installLibrary(lib: File) { /* load .so */ }
}
