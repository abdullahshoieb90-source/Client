package com.bedrock.vclient.runtime

class RuntimeManager(
    private val config: RuntimeConfig,
    private val runtime: Runtime
) {

    private var state: RuntimeState = RuntimeState.IDLE

    fun initialize() {
        if (state != RuntimeState.IDLE) return

        state = RuntimeState.INITIALIZING

        runtime.initialize()

        state = RuntimeState.READY
    }

    fun start() {
        if (state != RuntimeState.READY) return

        runtime.start()

        state = RuntimeState.RUNNING
    }

    fun stop() {
        if (state != RuntimeState.RUNNING) return

        runtime.stop()

        state = RuntimeState.STOPPED
    }

    fun destroy() {
        runtime.destroy()
        state = RuntimeState.IDLE
    }

    fun getState(): RuntimeState = state

    fun getConfig(): RuntimeConfig = config

    fun isRunning(): Boolean = runtime.isRunning()
}
