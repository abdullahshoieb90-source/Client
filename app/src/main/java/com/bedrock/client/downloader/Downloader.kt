
package com.bedrock.client.downloader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

class Downloader {
    data class Task(val url: String, val dest: File, var progress: Float = 0f)

    suspend fun download(url: String, dest: File, onProgress: (Float) -> Unit = {}): Boolean = withContext(Dispatchers.IO) {
        try {
            URL(url).openStream().use { input ->
                dest.outputStream().use { output -> input.copyTo(output) }
            }
            onProgress(100f)
            true
        } catch (e: Exception) { false }
    }

    // Resume, MultiThread, Queue placeholder
    suspend fun downloadWithResume(task: Task): Boolean = download(task.url, task.dest)
}
