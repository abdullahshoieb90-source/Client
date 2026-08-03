
#include "module_manager.h"
#include "impl/fps_counter.h"
#include "impl/cps_counter.h"
#include "impl/zoom.h"
#include "impl/fullbright.h"
#include "../logger/logger.h"

namespace bedrock::modules {

ModuleManager::ModuleManager() {
    // Register all modules - المودات المكتوبة ب C++
    registerModule(std::make_shared<FPSCounter>());
    registerModule(std::make_shared<CPSCounter>());
    registerModule(std::make_shared<Zoom>());
    registerModule(std::make_shared<Fullbright>());
}

ModuleManager& ModuleManager::getInstance() {
    static ModuleManager instance;
    return instance;
}

void ModuleManager::registerModule(std::shared_ptr<Module> mod) {
    modules.push_back(mod);
    LOGI("ModuleManager", "Registered module: %s", mod->name.c_str());
}

std::vector<std::shared_ptr<Module>> ModuleManager::getAll() { return modules; }

std::shared_ptr<Module> ModuleManager::getModule(const std::string& name) {
    for (auto& m : modules) if (m->name == name) return m;
    return nullptr;
}

void ModuleManager::onTick() {
    for (auto& m : modules) if (m->enabled) m->onTick();
}

void ModuleManager::onRender(float delta) {
    for (auto& m : modules) if (m->enabled) m->onRender(delta);
}

void ModuleManager::setEnabled(const std::string& name, bool enabled) {
    auto mod = getModule(name);
    if (!mod) return;
    if (mod->enabled == enabled) return;
    mod->enabled = enabled;
    if (enabled) mod->onEnable();
    else mod->onDisable();
}

} // namespace

extern "C" {
#include <jni.h>
JNIEXPORT void JNICALL Java_com_bedrock_client_modules_ModuleManager_nativeNotifyModuleState(JNIEnv* env, jobject thiz, jstring name, jboolean enabled) {
    const char* cName = env->GetStringUTFChars(name, nullptr);
    bedrock::modules::ModuleManager::getInstance().setEnabled(cName, enabled);
    env->ReleaseStringUTFChars(name, cName);
}
}
