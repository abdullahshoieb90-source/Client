
#include "imgui_handler.h"
#include "../logger/logger.h"
// #include "imgui.h" - third_party/imgui

namespace bedrock::imgui {

ImGuiHandler& ImGuiHandler::getInstance() {
    static ImGuiHandler instance;
    return instance;
}

void ImGuiHandler::initialize() {
    if (initialized) return;
    LOGI("ImGui", "Initializing ImGui: مكتبة واجهة رسومية لإظهار القوائم");
    // ImGui::CreateContext();
    // ImGui_ImplOpenGL3_Init();
    initialized = true;
}

void ImGuiHandler::beginFrame() {
    // ImGui_ImplOpenGL3_NewFrame();
    // ImGui::NewFrame();
}

void ImGuiHandler::renderOverlay(const std::unordered_map<std::string, std::string>& texts) {
    // Example overlay for FPS, CPS, Coordinates
    // ImGui::Begin("V Client Overlay", nullptr, ImGuiWindowFlags_NoDecoration | ....);
    // for (auto& [k,v] : texts) ImGui::Text("%s: %s", k.c_str(), v.c_str());
    // ImGui::End();
}

void ImGuiHandler::endFrame() {
    // ImGui::Render();
    // ImGui_ImplOpenGL3_RenderDrawData(ImGui::GetDrawData());
}

void ImGuiHandler::shutdown() {
    // ImGui_ImplOpenGL3_Shutdown();
    // ImGui::DestroyContext();
    initialized = false;
}

} // namespace
