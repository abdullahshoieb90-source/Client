
#pragma once
#include <string>
#include <unordered_map>
#include <functional>

namespace bedrock::hooks {

enum class HookResult { SUCCESS, FAILED, ALREADY_HOOKED };

class HookManager {
public:
    static HookManager& getInstance();

    HookResult installHook(const std::string& name, void* target, void* detour, void** original);
    HookResult removeHook(const std::string& name);
    void* getOriginal(const std::string& name);
    bool isHooked(const std::string& name);

    // Specific Minecraft hooks
    void installAll();
    void installRenderHooks();
    void installInputHooks();
    void installTickHooks();

private:
    HookManager() = default;
    struct HookInfo {
        void* target = nullptr;
        void* detour = nullptr;
        void* original = nullptr;
        bool installed = false;
    };
    std::unordered_map<std::string, HookInfo> hooks;
};

} // namespace
