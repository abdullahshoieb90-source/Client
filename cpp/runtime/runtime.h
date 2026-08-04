#pragma once

#include <jni.h>
#include <string>

namespace bedrock::runtime {

class Runtime {
public:
    static Runtime& getInstance();

    // Runtime lifecycle
    bool initialize(JNIEnv* env, jobject context);
    bool prepareEnvironment(const std::string& instancePath);

    // Minecraft runtime
    bool loadMinecraftLibraries();
    bool initializeMinecraft();
    bool launchMinecraft();

    // Runtime control
    bool start();
    void tick();
    void shutdown();

    // State
    bool isRunning() const {
        return running;
    }

private:
    Runtime() = default;
    ~Runtime() = default;

    Runtime(const Runtime&) = delete;
    Runtime& operator=(const Runtime&) = delete;

private:
    bool running = false;

    JNIEnv* env = nullptr;
    jobject contextRef = nullptr;

    std::string sandboxPath;
};

} // namespace bedrock::runtime
