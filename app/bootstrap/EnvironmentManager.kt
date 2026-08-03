package com.bedrock.client.bootstrap

import android.content.Context
import java.io.File

class EnvironmentManager(
    private val context: Context
) {

    fun prepare() {
        createDirectory("runtime")
        createDirectory("instances")
        createDirectory("cache")
        createDirectory("logs")
    }

    private fun createDirectory(name: String) {
        val dir = File(context.filesDir, name)

        if (!dir.exists()) {
            dir.mkdirs()
        }
    }
}
