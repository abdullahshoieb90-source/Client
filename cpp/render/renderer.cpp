
#include "renderer.h"
#include "../logger/logger.h"
#include "../modules/module_manager.h"
#include "../imgui/imgui_handler.h"

namespace bedrock::render {

Renderer& Renderer::getInstance() {
    static Renderer instance;
    return instance;
}

void Renderer::initialize() {
    if (initialized) return;
    LOGI("Renderer", "Initializing renderer: الرسم باستخدام OpenGL/Vulkan");
    imgui::ImGuiHandler::getInstance().initialize();
    initialized = true;
}

void Renderer::render() {
    if (!initialized) return;

    // Call module render hooks
    modules::ModuleManager::getInstance().onRender(1.0f/60.0f);

    // Render ImGui overlay
    if (imguiEnabled) {
        imgui::ImGuiHandler::getInstance().beginFrame();
        imgui::ImGuiHandler::getInstance().renderOverlay(overlayTexts);
        imgui::ImGuiHandler::getInstance().endFrame();
    }
}

void Renderer::shutdown() {
    imgui::ImGuiHandler::getInstance().shutdown();
    initialized = false;
}

void Renderer::setOverlayText(const std::string& key, const std::string& value) {
    std::lock_guard<std::mutex> lock(mtx);
    overlayTexts[key] = value;
}

void Renderer::enableImGui(bool enable) { imguiEnabled = enable; }

} // namespace
