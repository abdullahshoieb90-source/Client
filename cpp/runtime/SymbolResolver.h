#pragma once

#include <string>

namespace bedrock::runtime {

class SymbolResolver {
public:
    static SymbolResolver& getInstance();

    bool initialize(void* minecraftHandle);

    void* find(const std::string& symbol);

private:
    SymbolResolver() = default;
    ~SymbolResolver() = default;

    SymbolResolver(const SymbolResolver&) = delete;
    SymbolResolver& operator=(const SymbolResolver&) = delete;

private:
    void* handle = nullptr;
};

} // namespace bedrock::runtime
