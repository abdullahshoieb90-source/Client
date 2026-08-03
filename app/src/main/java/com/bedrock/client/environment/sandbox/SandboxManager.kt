
package com.bedrock.client.environment.sandbox
import android.content.Context
import java.io.File

class SandboxManager(private val context: Context) {
    fun createSandbox(instanceId: String): File {
        // يصنع Sandbox مثل /data/user/0/app/files/game/ بدل استخدام ملفات اللعبة الأصلية
        val sandbox = File(context.filesDir, "sandbox/$instanceId")
        sandbox.mkdirs()
        File(sandbox, "games").mkdirs()
        File(sandbox, "worlds").mkdirs()
        return sandbox
    }
    fun getSandboxPath(instanceId: String): File = File(context.filesDir, "sandbox/$instanceId")
}
