package com.bedrock.vclient.runtime

import java.io.File

data class RuntimeConfig(

    /** Minecraft package name */
    val packageName: String,

    /** Path to the installed APK */
    val apkPath: String,

    /** Native libraries directory */
    val nativeLibraryDir: File,

    /** Client files directory */
    val filesDir: File,

    /** Client cache directory */
    val cacheDir: File,

    /** Sandbox directory */
    val sandboxDir: File,

    /** Game data directory inside sandbox */
    val gameDataDir: File
)
