
package com.bedrock.client.extractor
import java.io.File
import java.util.zip.ZipFile

class Extractor {

    
    fun extractApk(apk: File, dest: File, abi: String) {
        dest.mkdirs()
        val prefix = "lib/$abi/"
        ZipFile(apk).use { zip ->
            zip.entries().asSequence()
                .filter { !it.isDirectory && it.name.startsWith(prefix) && it.name.endsWith(".so") }
                .forEach { entry ->
                    val out = File(dest, entry.name.substringAfterLast('/'))
                    zip.getInputStream(entry).use { input ->
                        out.outputStream().use { output -> input.copyTo(output) }
                    }
                }
        }
    }

    fun extractZip(zip: File, dest: File) { ZipFile(zip).use { z -> z.entries().asSequence().forEach { e -> val out = File(dest, e.name); if (e.isDirectory) out.mkdirs() else { out.parentFile?.mkdirs(); z.getInputStream(e).copyTo(out.outputStream()) } } } }
    fun extractJar(jar: File, dest: File) = extractZip(jar, dest)
}
