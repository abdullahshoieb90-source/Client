
#pragma once
#include <string>

namespace bedrock::loader {

class Loader {
public:
    static Loader& getInstance();
    void* loadLibrary(const std::string& name);
    void* loadFromPath(const std::string& path);
    bool unload(void* handle);
    void* getSymbol(void* handle, const std::string& symbol);

private:
    Loader() = default;
};

} // namespace
