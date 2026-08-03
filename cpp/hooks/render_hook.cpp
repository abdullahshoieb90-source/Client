
#include "render_hook.h"
#include "../render/renderer.h"
#include "../logger/logger.h"

namespace bedrock::hooks {

void hooked_eglSwapBuffers() {
    // Before swap, render overlay
    bedrock::render::Renderer::getInstance().render();
    // Call original
}

void* getEglSwapBuffers() { return nullptr; }

}
