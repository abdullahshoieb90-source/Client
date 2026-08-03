
package com.bedrock.client.utils
import android.content.Context
import java.io.File

object FileUtils {
    fun getFileSize(f: File): Long = if (f.isFile) f.length() else f.walkTopDown().filter { it.isFile }.map { it.length() }.sum()
    fun humanReadable(size: Long): String {
        val kb = size / 1024.0
        val mb = kb / 1024.0
        return if (mb >= 1) "%.2f MB".format(mb) else "%.2f KB".format(kb)
    }
}

object DeviceUtils {
    fun getDeviceInfo(): String = android.os.Build.MODEL + " " + android.os.Build.VERSION.RELEASE
}

object TimeUtils {
    fun now(): Long = System.currentTimeMillis()
}
