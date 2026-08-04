#include "symbol_resolver.h"

#include <dlfcn.h>

#include "../logger/logger.h"

namespace bedrock::runtime {

SymbolResolver& SymbolResolver::getInstance() {
    static SymbolResolver instance;
    return instance;
}

bool SymbolResolver::initialize(void* minecraftHandle) {

    if (minecraftHandle == nullptr) {
        LOGE("SymbolResolver", "Minecraft handle is null");
        return false;
    }

    handle = minecraftHandle;

    LOGI("SymbolResolver", "Initialized");

    return true;
}

void* SymbolResolver::find(const std::string& symbol) {

    if (handle == nullptr)
        return nullptr;

    void* address = dlsym(handle, symbol.c_str());

    if (address == nullptr) {
        LOGE("SymbolResolver",
             "Symbol not found: %s",
             symbol.c_str());
    }

    return address;
}

} // namespace bedrock::runtime
