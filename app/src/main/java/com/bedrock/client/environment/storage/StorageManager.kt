
package com.bedrock.client.environment.storage
import android.content.Context
import android.os.Environment
import java.io.File

class StorageManager(private val context: Context) {
    fun getInternal(): File = context.filesDir
    fun getExternal(): File? = context.getExternalFilesDir(null)
    fun getScopedStorage(): File = context.filesDir // Scoped Storage compat
    fun isExternalAvailable(): Boolean = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}
