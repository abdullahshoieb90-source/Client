
#pragma once
#include <jni.h>
#include <string>
#include <functional>
#include <unordered_map>

namespace bedrock::bridge {

class Bridge {
public:
    static Bridge& getInstance();

    bool initialize(JNIEnv* env);
    void attachActivity(JNIEnv* env, jobject activity);
    void detachActivity();
    
    // Called from Java
    JavaVM* getJvm() { return jvm; }
    JNIEnv* getEnv();

    // JNI <-> Java/Kotlin
    void callJavaMethod(const std::string& method, const std::string& args);
    int launchGame(const std::string& instancePath, const std::string& version);

    // For modules to notify Java
    void notifyModuleState(const std::string& name, bool enabled);
    void logToJava(int level, const std::string& tag, const std::string& msg);

private:
    Bridge() = default;
    JavaVM* jvm = nullptr;
    jobject activityRef = nullptr;
    jobject bridgeManagerRef = nullptr;
    jclass bridgeManagerClass = nullptr;

    std::unordered_map<std::string, jmethodID> methodCache;
};

} // namespace bedrock::bridge
