#pragma once

#include <string>
#include <unordered_map>

namespace bedrock::runtime {

class LibraryLoader {
public:
    static LibraryLoader& getInstance();

    bool load();
    bool initialize();
    void unload();

    void* getLibraryHandle(const std::string& name) const;

private:
    LibraryLoader() = default;
    ~LibraryLoader() = default;

    LibraryLoader(const LibraryLoader&) = delete;
    LibraryLoader& operator=(const LibraryLoader&) = delete;

private:
    bool loadLibrary(const std::string& name, const std::string& path);

private:
    std::unordered_map<std::string, void*> libraries;
};

} // namespace bedrock::runtime
