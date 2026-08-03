
package com.bedrock.client.cache
import android.content.Context
import java.io.File

class CacheManager(private val context: Context) {
    fun clear() { context.cacheDir.deleteRecursively(); context.cacheDir.mkdirs() }
    fun getSize(): Long = context.cacheDir.walkTopDown().filter { it.isFile }.map { it.length() }.sum()
}
