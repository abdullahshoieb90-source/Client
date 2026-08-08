
#include "bootstrap.h"
#include "../logger/logger.h"
#include "../loader/loader.h"
#include "../runtime/runtime.h"
#include "../runtime/RuntimeConfig.h"
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

    // IMPORTANT: never dlopen("minecraftpe") or dlopen("libminecraftpe.so") here — a bare
    // name search walks the process's default linker namespace and can silently pick up
    // whatever copy the OS already has loaded (e.g. from an installed Minecraft app),
    // defeating per-instance isolation. Always resolve the full absolute path that was
    // set for *this* instance via RuntimeConfig (populated from
    // FakeApplicationInfoFactory / RuntimeManager on the Kotlin side).
    const std::string libPath = runtime::RuntimeConfig::getInstance().getMinecraftLibraryPath();

    if (libPath.empty() || libPath == "/libminecraftpe.so") {
        LOGE("Bootstrap", "No instance native library path configured — call "
                           "RuntimeManager.prepare()/prepareImported() before launching.");
        return false;
    }

    minecraftHandle = loader.loadFromPath(libPath);
    return minecraftHandle != nullptr;
}

void Bootstrap::shutdown() {
    LOGI("Bootstrap", "Shutting down");
    runtime::Runtime::getInstance().shutdown();
    if (minecraftHandle) dlclose(minecraftHandle);
}

} // namespace
