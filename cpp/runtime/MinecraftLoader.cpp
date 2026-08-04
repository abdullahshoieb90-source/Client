#include "MinecraftLoader.h"

#include "../logger/logger.h"
#include "LibraryLoader.h"
#include "SymbolResolver.h"

namespace bedrock::runtime {

MinecraftLoader& MinecraftLoader::getInstance() {
    static MinecraftLoader instance;
    return instance;
}

bool MinecraftLoader::initialize() {

    void* handle =
        LibraryLoader::getInstance().getLibraryHandle("minecraft");

    if (handle == nullptr) {
        LOGE("MinecraftLoader", "Minecraft library is not loaded.");
        return false;
    }

    initialized = true;

    LOGI("MinecraftLoader", "Minecraft initialized.");

    return true;
}

bool MinecraftLoader::launch() {

    if (!initialized) {
        LOGE("MinecraftLoader", "Minecraft is not initialized.");
        return false;
    }

    LOGI("MinecraftLoader", "Preparing Minecraft launch.");

    // TODO:
    // Resolve required symbols with SymbolResolver
    // Initialize the runtime
    // Call the correct Minecraft entry point

    return true;
}

void MinecraftLoader::shutdown() {

    initialized = false;

    LOGI("MinecraftLoader", "Minecraft shutdown.");
}

} // namespace bedrock::runtime
