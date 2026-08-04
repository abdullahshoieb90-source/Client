#pragma once

namespace bedrock::runtime {

class MinecraftLoader {
public:
    static MinecraftLoader& getInstance();

    bool initialize();
    bool launch();
    void shutdown();

private:
    MinecraftLoader() = default;
    ~MinecraftLoader() = default;

    MinecraftLoader(const MinecraftLoader&) = delete;
    MinecraftLoader& operator=(const MinecraftLoader&) = delete;

private:
    bool initialized = false;
};

} // namespace bedrock::runtime
