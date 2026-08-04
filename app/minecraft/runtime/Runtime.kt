package com.bedrock.client.minecraft.runtime

interface Runtime {

    /**
     * Initialize runtime resources.
     */
    fun initialize()

    /**
     * Start the runtime.
     */
    fun start()

    /**
     * Stop the runtime.
     */
    fun stop()

    /**
     * Release runtime resources.
     */
    fun destroy()

    /**
     * Returns true if the runtime is running.
     */
    fun isRunning(): Boolean
}
