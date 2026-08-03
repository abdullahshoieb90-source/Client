
package com.bedrock.client.logger
import android.content.Context
import android.util.Log
import timber.log.Timber
import java.io.File

object Logger {
    fun init(context: Context) {
        if (Timber.forest().isEmpty()) {
            Timber.plant(Timber.DebugTree())
            Timber.plant(FileTree(File(context.filesDir, "logs")))
        }
    }
    fun i(tag: String, msg: String) = Timber.tag(tag).i(msg)
    fun e(tag: String, msg: String, e: Throwable? = null) = Timber.tag(tag).e(e, msg)
    fun d(tag: String, msg: String) = Timber.tag(tag).d(msg)
    fun nativeLog(level: Int, tag: String, msg: String) {
        when(level) { 0 -> d(tag, msg); 1 -> i(tag, msg); 2 -> e(tag, msg) }
    }

    class FileTree(private val dir: File) : Timber.Tree() {
        init { dir.mkdirs() }
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            try {
                val f = File(dir, "client.log")
                f.appendText("${System.currentTimeMillis()} $tag: $message\n")
                if (t != null) f.appendText(t.stackTraceToString() + "\n")
            } catch (_: Exception) {}
        }
    }
}
