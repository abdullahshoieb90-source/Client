package com.bedrock.client.minecraft.runtime

enum class RuntimeState {

    /**
     * Runtime has not been initialized.
     */
    IDLE,

    /**
     * Runtime is preparing.
     */
    INITIALIZING,

    /**
     * Runtime is ready to launch.
     */
    READY,

    /**
     * Minecraft is running.
     */
    RUNNING,

    /**
     * Runtime has been stopped.
     */
    STOPPED,

    /**
     * Runtime failed.
     */
    ERROR
}
