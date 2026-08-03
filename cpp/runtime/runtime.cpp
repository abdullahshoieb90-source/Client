
#include "runtime.h"
#include "../hooks/hook_manager.h"
#include "../modules/module_manager.h"
#include "../render/renderer.h"
#include "../logger/logger.h"

namespace bedrock::runtime {

Runtime& Runtime::getInstance() {
    static Runtime instance;
    return instance;
}

bool Runtime::initialize(JNIEnv* e, jobject ctx) {
    env = e;
    contextRef = e->NewGlobalRef(ctx);
    LOGI("Runtime", "Runtime initializing: cpp/runtime/ إدارة دورة حياة الجزء الأصلي");

    // Initialize renderer
    render::Renderer::getInstance().initialize();

    // Module manager already initialized via constructor
    return true;
}

bool Runtime::prepareEnvironment(const std::string& instancePath) {
    LOGI("Runtime", "Preparing environment for %s", instancePath.c_str());
    // Prepare sandbox filesystem
    return true;
}

void Runtime::start() {
    LOGI("Runtime", "Runtime starting - installing hooks");
    hooks::HookManager::getInstance().installAll();
    running = true;
}

void Runtime::tick() {
    if (!running) return;
    modules::ModuleManager::getInstance().onTick();
}

void Runtime::shutdown() {
    LOGI("Runtime", "Runtime shutting down");
    running = false;
    if (env && contextRef) env->DeleteGlobalRef(contextRef);
}

} // namespace
