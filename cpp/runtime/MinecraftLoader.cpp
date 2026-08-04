#include "minecraft_loader.h"

#include "../logger/logger.h"
#include "symbol_resolver.h"

namespace bedrock::runtime {

MinecraftLoader& MinecraftLoader::getInstance() {
    static MinecraftLoader instance;
    return instance;
}

bool MinecraftLoader::initialize() {

    LOGI("MinecraftLoader", "Initializing Minecraft");

    // لاحقًا سيتم البحث عن الرموز المطلوبة:
    //
    // auto symbol =
    //     SymbolResolver::getInstance().find("...");
    //
    // if (!symbol)
    //     return false;

    initialized = true;

    return true;
}

bool MinecraftLoader::launch() {

    if (!initialized) {
        LOGE("MinecraftLoader", "Minecraft is not initialized");
        return false;
    }

    LOGI("MinecraftLoader", "Launching Minecraft");

    // لاحقًا:
    // - استدعاء JNI_OnLoad إن لزم
    // - استدعاء نقطة الدخول المناسبة
    // - تهيئة NativeActivity أو أي نقطة بدء حسب إصدار اللعبة

    return true;
}

void MinecraftLoader::shutdown() {

    LOGI("MinecraftLoader", "Shutting down Minecraft");

    initialized = false;
}

} // namespace bedrock::runtime
