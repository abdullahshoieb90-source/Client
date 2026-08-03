
#include "bootstrap.h"
#include "../logger/logger.h"
#include "../loader/loader.h"
#include "../runtime/runtime.h"
#include "../bridge/bridge.h"
#include <dlfcn.h>
#include <android/log.h>

namespace bedrock::bootstrap {

Bootstrap& Bootstrap::getInstance() {
    static Bootstrap instance;
    return instance;
}

bool Bootstrap::initialize(JNIEnv* env, jobject context) {
    LOGI("Bootstrap", "Initializing native bootstrap: Application -> Bootstrap -> Launcher");
    
    // Initialize logger
    logger::Logger::init();

    // Initialize runtime
    runtime::Runtime::getInstance().initialize(env, context);

    // Initialize bridge
    bridge::Bridge::getInstance().initialize(env);

    LOGI("Bootstrap", "Bootstrap initialized successfully");
    return true;
}

bool Bootstrap::launchMinecraft(const std::string& instancePath, const std::string& version) {
    LOGI("Bootstrap", "Launching Minecraft: instance=%s version=%s", instancePath.c_str(), version.c_str());

    auto& rt = runtime::Runtime::getInstance();
    if (!rt.prepareEnvironment(instancePath)) {
        LOGE("Bootstrap", "Failed to prepare environment");
        return false;
    }

    // Load libminecraftpe.so original via loader
    if (!loadOriginalMinecraft()) {
        LOGE("Bootstrap", "Failed to load original Minecraft library");
        return false;
    }

    rt.start();
    return true;
}

bool Bootstrap::loadOriginalMinecraft() {
    loader::Loader& loader = loader::Loader::getInstance();
    minecraftHandle = loader.loadLibrary("minecraftpe");
    if (!minecraftHandle) {
        // Try alternative paths
        minecraftHandle = dlopen("libminecraftpe.so", RTLD_NOW);
    }
    return minecraftHandle != nullptr;
}

void Bootstrap::shutdown() {
    LOGI("Bootstrap", "Shutting down");
    runtime::Runtime::getInstance().shutdown();
    if (minecraftHandle) dlclose(minecraftHandle);
}

} // namespace
