
#pragma once
#include <string>
#include <unordered_map>
#include <mutex>

namespace bedrock::render {

class Renderer {
public:
    static Renderer& getInstance();
    void initialize();
    void render();
    void shutdown();

    void setOverlayText(const std::string& key, const std::string& value);
    void enableImGui(bool enable);

private:
    Renderer() = default;
    std::unordered_map<std::string, std::string> overlayTexts;
    std::mutex mtx;
    bool imguiEnabled = true;
    bool initialized = false;
};

} // namespace
