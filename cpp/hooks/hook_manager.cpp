
#include "hook_manager.h"
#include "../logger/logger.h"
#include "../memory/memory.h"

namespace bedrock::hooks {

HookManager& HookManager::getInstance() {
    static HookManager instance;
    return instance;
}

HookResult HookManager::installHook(const std::string& name, void* target, void* detour, void** original) {
    if (hooks.find(name) != hooks.end() && hooks[name].installed) {
        return HookResult::ALREADY_HOOKED;
    }
    LOGI("HookManager", "Installing hook: %s target=%p detour=%p", name.c_str(), target, detour);

    // Here integrate Dobby or MinHook
    // For now placeholder using memory patcher
    // DobbyHook(target, detour, original);

    HookInfo info;
    info.target = target;
    info.detour = detour;
    info.original = original ? *original : nullptr;
    info.installed = true;
    hooks[name] = info;

    return HookResult::SUCCESS;
}

HookResult HookManager::removeHook(const std::string& name) {
    auto it = hooks.find(name);
    if (it == hooks.end()) return HookResult::FAILED;
    // DobbyDestroy(it->second.target);
    it->second.installed = false;
    LOGI("HookManager", "Removed hook: %s", name.c_str());
    return HookResult::SUCCESS;
}

void* HookManager::getOriginal(const std::string& name) {
    auto it = hooks.find(name);
    return it != hooks.end() ? it->second.original : nullptr;
}

bool HookManager::isHooked(const std::string& name) {
    auto it = hooks.find(name);
    return it != hooks.end() && it->second.installed;
}

void HookManager::installAll() {
    installRenderHooks();
    installInputHooks();
    installTickHooks();
    LOGI("HookManager", "All hooks installed");
}

void HookManager::installRenderHooks() {
    LOGI("HookManager", "Installing render hooks (OpenGL/Vulkan overlay)");
    // Hook eglSwapBuffers, glClear, etc for ImGui rendering
}

void HookManager::installInputHooks() {
    LOGI("HookManager", "Installing input hooks for CPS counter");
    // Hook touch input, mouse
}

void HookManager::installTickHooks() {
    LOGI("HookManager", "Installing tick hooks");
    // Hook ClientInstance::update, Level::tick etc
}

} // namespace

extern "C" {
JNIEXPORT jboolean JNICALL Java_com_bedrock_client_hook_HookManager_nativeInstallHook(JNIEnv* env, jobject thiz, jstring name) {
    const char* cName = env->GetStringUTFChars(name, nullptr);
    auto result = bedrock::hooks::HookManager::getInstance().installHook(cName, nullptr, nullptr, nullptr);
    env->ReleaseStringUTFChars(name, cName);
    return result == bedrock::hooks::HookResult::SUCCESS;
}
JNIEXPORT jboolean JNICALL Java_com_bedrock_client_hook_HookManager_nativeRemoveHook(JNIEnv* env, jobject thiz, jstring name) {
    const char* cName = env->GetStringUTFChars(name, nullptr);
    auto result = bedrock::hooks::HookManager::getInstance().removeHook(cName);
    env->ReleaseStringUTFChars(name, cName);
    return result == bedrock::hooks::HookResult::SUCCESS;
}
}
