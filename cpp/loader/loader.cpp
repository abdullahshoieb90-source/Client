
#include "loader.h"
#include "../logger/logger.h"
#include <dlfcn.h>

namespace bedrock::loader {

Loader& Loader::getInstance() {
    static Loader instance;
    return instance;
}

void* Loader::loadLibrary(const std::string& name) {
    LOGI("Loader", "Loading library: %s", name.c_str());
    void* handle = dlopen(name.c_str(), RTLD_NOW);
    if (!handle) {
        // try with lib prefix
        std::string alt = "lib" + name + ".so";
        handle = dlopen(alt.c_str(), RTLD_NOW);
    }
    if (!handle) LOGE("Loader", "Failed to load %s: %s", name.c_str(), dlerror());
    return handle;
}

void* Loader::loadFromPath(const std::string& path) {
    LOGI("Loader", "Loading from path: %s", path.c_str());
    return dlopen(path.c_str(), RTLD_NOW);
}

bool Loader::unload(void* handle) {
    return dlclose(handle) == 0;
}

void* Loader::getSymbol(void* handle, const std::string& symbol) {
    return dlsym(handle, symbol.c_str());
}

} // namespace
