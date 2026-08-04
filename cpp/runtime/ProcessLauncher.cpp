#include "process_launcher.h"

#include <dlfcn.h>

#include "../logger/logger.h"
#include "library_loader.h"

namespace bedrock::runtime {

ProcessLauncher& ProcessLauncher::getInstance() {
    static ProcessLauncher instance;
    return instance;
}

bool ProcessLauncher::launch() {

    LOGI("ProcessLauncher", "Launching Minecraft");

    void* minecraft =
        LibraryLoader::getInstance().getLibraryHandle("minecraft");

    if (minecraft == nullptr) {

        LOGE("ProcessLauncher",
             "Minecraft library is not loaded.");

        return false;
    }

    // سيتم لاحقًا:
    //
    // - Resolve JNI_OnLoad
    // - Resolve ANativeActivity_onCreate
    // - Resolve Minecraft symbols
    // - Start Minecraft runtime

    launched = true;

    LOGI("ProcessLauncher", "Minecraft launch prepared");

    return true;
}

void ProcessLauncher::shutdown() {

    if (!launched)
        return;

    LOGI("ProcessLauncher", "Stopping Minecraft runtime");

    // Cleanup سيضاف لاحقًا

    launched = false;
}

} // namespace bedrock::runtime
