
#pragma once
#include <string>
#include <unordered_map>

namespace bedrock::imgui {

class ImGuiHandler {
public:
    static ImGuiHandler& getInstance();
    void initialize();
    void beginFrame();
    void renderOverlay(const std::unordered_map<std::string, std::string>& texts);
    void endFrame();
    void shutdown();
private:
    ImGuiHandler() = default;
    bool initialized = false;
};

} // namespace
