
package com.bedrock.client.behaviorpacks
import android.content.Context
import java.io.File

class BehaviorPackManager(private val context: Context) {
    fun getPacks(): List<File> {
        val dir = File(context.filesDir, "behavior_packs")
        return dir.listFiles()?.toList() ?: emptyList()
    }
}
