
package com.bedrock.client.extractor
import java.io.File
import java.util.zip.ZipFile

class Extractor {
    fun extractApk(apk: File, dest: File) { /* unzip lib */ ZipFile(apk).use { /* ... */ } }
    fun extractZip(zip: File, dest: File) { ZipFile(zip).use { z -> z.entries().asSequence().forEach { e -> val out = File(dest, e.name); if (e.isDirectory) out.mkdirs() else { out.parentFile?.mkdirs(); z.getInputStream(e).copyTo(out.outputStream()) } } } }
    fun extractJar(jar: File, dest: File) = extractZip(jar, dest)
}
