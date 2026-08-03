
#pragma once
#include <jni.h>
#include <string>

namespace bedrock::bootstrap {

class Bootstrap {
public:
    static Bootstrap& getInstance();
    bool initialize(JNIEnv* env, jobject context);
    bool launchMinecraft(const std::string& instancePath, const std::string& version);
    void shutdown();

private:
    Bootstrap() = default;
    bool loadOriginalMinecraft();
    void* minecraftHandle = nullptr;
};

} // namespace bedrock::bootstrap
