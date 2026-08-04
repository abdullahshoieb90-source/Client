package com.vclient.runtime

object NativeBridge {

    init {
        System.loadLibrary("vclient")
    }

    external fun initializeRuntime(
        apkPath: String,
        nativeLibraryDir: String,
        sandboxDir: String
    ): Boolean

    external fun loadMinecraft(): Boolean

    external fun startMinecraft(): Boolean

    external fun shutdownRuntime()
}
