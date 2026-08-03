
package com.bedrock.client.crash
import android.content.Context
import com.bedrock.client.logger.Logger
import java.io.File

class CrashHandler private constructor(private val context: Context) : Thread.UncaughtExceptionHandler {
    private val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(t: Thread, e: Throwable) {
        try {
            val crashDir = File(context.filesDir, "crash")
            crashDir.mkdirs()
            val file = File(crashDir, "crash_${System.currentTimeMillis()}.log")
            file.writeText(e.stackTraceToString())
            Logger.e("CrashHandler", "Crash captured: ${file.absolutePath}", e)
        } catch (_: Exception) {}
        defaultHandler?.uncaughtException(t, e)
    }

    companion object {
        fun init(context: Context) {
            val handler = CrashHandler(context)
            Thread.setDefaultUncaughtExceptionHandler(handler)
        }
    }
}
