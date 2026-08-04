#include "ProcessLauncher.h"

#include "../logger/logger.h"
#include "LibraryLoader.h"
#include "MinecraftLoader.h"

namespace bedrock::runtime {

ProcessLauncher& ProcessLauncher::getInstance() {
    static ProcessLauncher instance;
    return instance;
}

bool ProcessLauncher::launch() {

    LOGI("ProcessLauncher", "Launching Minecraft");

    // التأكد من تحميل libminecraftpe.so
    void* minecraft =
        LibraryLoader::getInstance().getLibraryHandle("minecraft");

    if (minecraft == nullptr) {

        LOGE("ProcessLauncher",
             "Minecraft library is not loaded.");

        return false;
    }

    // تهيئة Minecraft Runtime
    MinecraftLoader& loader = MinecraftLoader::getInstance();

    if (!loader.initialize()) {

        LOGE("ProcessLauncher",
             "Failed to initialize Minecraft.");

        return false;
    }

    // تشغيل Minecraft
    if (!loader.launch()) {

        LOGE("ProcessLauncher",
             "Failed to launch Minecraft.");

        return false;
    }

    launched = true;

    LOGI("ProcessLauncher",
         "Minecraft started successfully.");

    return true;
}

void ProcessLauncher::shutdown() {

    if (!launched)
        return;

    LOGI("ProcessLauncher",
         "Stopping Minecraft runtime");

    MinecraftLoader::getInstance().shutdown();

    launched = false;

    LOGI("ProcessLauncher",
         "Minecraft stopped.");
}

} // namespace bedrock::runtime
