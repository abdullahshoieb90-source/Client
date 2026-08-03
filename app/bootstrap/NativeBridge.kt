package com.bedrock.client.bootstrap

object NativeBridge {

    init {
        try {
            System.loadLibrary("client")
        } catch (e: UnsatisfiedLinkError) {
            e.printStackTrace()
        }
    }

    external fun initialize(): Boolean

    external fun shutdown()

    external fun getVersion(): String
}
