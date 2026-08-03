
package com.bedrock.client.backup
import android.content.Context
import java.io.File
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

class BackupManager(private val context: Context) {
    fun backup(source: File, dest: File) {
        dest.parentFile?.mkdirs()
        ZipOutputStream(dest.outputStream()).use { zos ->
            source.walkTopDown().filter { it.isFile }.forEach { file ->
                val entry = java.util.zip.ZipEntry(file.relativeTo(source).path)
                zos.putNextEntry(entry)
                file.inputStream().copyTo(zos)
                zos.closeEntry()
            }
        }
    }
    fun restore(backup: File, target: File) { /* unzip */ }
}
