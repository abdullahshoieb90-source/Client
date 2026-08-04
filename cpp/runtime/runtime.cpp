#include "runtime.h"

#include "../hooks/hook_manager.h"
#include "../logger/logger.h"
#include "../modules/module_manager.h"
#include "../render/renderer.h"
#include "library_loader.h"
#include "process_launcher.h"

namespace bedrock::runtime {

Runtime& Runtime::getInstance() {
    static Runtime instance;
    return instance;
}

bool Runtime::initialize(JNIEnv* e, jobject ctx) {
    env = e;
    contextRef = e->NewGlobalRef(ctx);

    LOGI("Runtime", "Initializing V Client Runtime");

    render::Renderer::getInstance().initialize();

    running = false;

    return true;
}

bool Runtime::prepareEnvironment(const std::string& instancePath) {
    LOGI("Runtime", "Preparing sandbox: %s", instancePath.c_str());

    sandboxPath = instancePath;

    // سيتم لاحقًا:
    // - إنشاء مجلدات الـ Sandbox
    // - إعداد ملفات اللعبة
    // - إعداد الـ Assets
    // - إعداد ملفات الحفظ

    return true;
}

bool Runtime::loadMinecraftLibraries() {
    LOGI("Runtime", "Loading Minecraft native libraries");

    return LibraryLoader::getInstance().load();
}

bool Runtime::initializeMinecraft() {
    LOGI("Runtime", "Initializing Minecraft runtime");

    return LibraryLoader::getInstance().initialize();
}

bool Runtime::launchMinecraft() {
    LOGI("Runtime", "Launching Minecraft");

    return ProcessLauncher::getInstance().launch();
}

bool Runtime::start() {
    LOGI("Runtime", "Starting Runtime");

    if (!loadMinecraftLibraries()) {
        LOGE("Runtime", "Failed to load Minecraft libraries");
        return false;
    }

    if (!initializeMinecraft()) {
        LOGE("Runtime", "Failed to initialize Minecraft");
        return false;
    }

    hooks::HookManager::getInstance().installAll();

    if (!launchMinecraft()) {
        LOGE("Runtime", "Failed to launch Minecraft");
        return false;
    }

    running = true;

    LOGI("Runtime", "Runtime started successfully");

    return true;
}

void Runtime::tick() {
    if (!running)
        return;

    modules::ModuleManager::getInstance().onTick();
}

void Runtime::shutdown() {
    LOGI("Runtime", "Shutting down Runtime");

    running = false;

    ProcessLauncher::getInstance().shutdown();

    LibraryLoader::getInstance().unload();

    if (env && contextRef) {
        env->DeleteGlobalRef(contextRef);
        contextRef = nullptr;
    }

    env = nullptr;

    LOGI("Runtime", "Runtime shutdown complete");
}

} // namespace bedrock::runtime
