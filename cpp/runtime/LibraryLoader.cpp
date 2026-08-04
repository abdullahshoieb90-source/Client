#include "LibraryLoader.h"

#include <dlfcn.h>

#include "../logger/logger.h"

namespace bedrock::runtime {

LibraryLoader& LibraryLoader::getInstance() {
    static LibraryLoader instance;
    return instance;
}

bool LibraryLoader::load() {

    LOGI("LibraryLoader", "Loading Minecraft libraries");

    // سيتم لاحقًا استبدال المسار بالمسار الحقيقي القادم من RuntimeConfig
    const std::string mcLib =
    RuntimeConfig::getInstance().getMinecraftLibraryPath();

    return loadLibrary("minecraft", mcLib);
}

bool LibraryLoader::initialize() {

    LOGI("LibraryLoader", "Initializing loaded libraries");

    // لاحقًا:
    // dlsym(...)
    // JNI_OnLoad(...)
    // Resolve symbols

    return true;
}

void LibraryLoader::unload() {

    LOGI("LibraryLoader", "Unloading libraries");

    for (auto& library : libraries) {

        if (library.second != nullptr) {
            dlclose(library.second);
        }
    }

    libraries.clear();
}

bool LibraryLoader::loadLibrary(
        const std::string& name,
        const std::string& path) {

    LOGI("LibraryLoader", "Loading %s", path.c_str());

    void* handle = dlopen(path.c_str(), RTLD_NOW);

    if (handle == nullptr) {

        LOGE("LibraryLoader",
             "dlopen failed: %s",
             dlerror());

        return false;
    }

    libraries[name] = handle;

    LOGI("LibraryLoader", "%s loaded successfully", name.c_str());

    return true;
}

void* LibraryLoader::getLibraryHandle(
        const std::string& name) const {

    auto it = libraries.find(name);

    if (it == libraries.end())
        return nullptr;

    return it->second;
}

} // namespace bedrock::runtime
