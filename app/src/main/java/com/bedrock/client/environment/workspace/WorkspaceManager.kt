
package com.bedrock.client.environment.workspace
import android.content.Context
import java.io.File

class WorkspaceManager private constructor(private val context: Context) {
    fun prepare() {
        File(context.filesDir, "workspace").mkdirs()
        File(context.filesDir, "workspace/cache").mkdirs()
        File(context.filesDir, "workspace/tmp").mkdirs()
    }
    fun getWorkspace(): File = File(context.filesDir, "workspace")
    fun getCache(): File = File(context.filesDir, "workspace/cache")
    fun clearCache() { getCache().deleteRecursively(); getCache().mkdirs() }

    companion object {
        @Volatile private var INSTANCE: WorkspaceManager? = null
        fun getInstance(ctx: Context) = INSTANCE ?: synchronized(this) { INSTANCE ?: WorkspaceManager(ctx).also { INSTANCE = it } }
    }
}
