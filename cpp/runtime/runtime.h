
#pragma once
#include <jni.h>
#include <string>

namespace bedrock::runtime {

class Runtime {
public:
    static Runtime& getInstance();
    bool initialize(JNIEnv* env, jobject context);
    bool prepareEnvironment(const std::string& instancePath);
    void start();
    void tick();
    void shutdown();
    bool isRunning() const { return running; }

private:
    Runtime() = default;
    bool running = false;
    JNIEnv* env = nullptr;
    jobject contextRef = nullptr;
};

} // namespace
