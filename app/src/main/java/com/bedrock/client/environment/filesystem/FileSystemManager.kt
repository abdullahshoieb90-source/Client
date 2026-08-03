
package com.bedrock.client.environment.filesystem
import java.io.File

class FileSystemManager {
    fun copy(src: File, dst: File) = src.copyTo(dst, overwrite = true)
    fun delete(f: File) = f.deleteRecursively()
    fun move(src: File, dst: File) = src.renameTo(dst)
    fun ensureDir(f: File) = f.mkdirs()
}
