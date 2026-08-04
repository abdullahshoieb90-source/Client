#include "RuntimeConfig.h"

namespace bedrock::runtime {

RuntimeConfig& RuntimeConfig::getInstance() {
    static RuntimeConfig instance;
    return instance;
}

void RuntimeConfig::setMinecraftPackage(const std::string& value) {
    minecraftPackage = value;
}

void RuntimeConfig::setApkPath(const std::string& value) {
    apkPath = value;
}

void RuntimeConfig::setNativeLibraryDir(const std::string& value) {
    nativeLibraryDir = value;
}

void RuntimeConfig::setSandboxPath(const std::string& value) {
    sandboxPath = value;
}

const std::string& RuntimeConfig::getMinecraftPackage() const {
    return minecraftPackage;
}

const std::string& RuntimeConfig::getApkPath() const {
    return apkPath;
}

const std::string& RuntimeConfig::getNativeLibraryDir() const {
    return nativeLibraryDir;
}

const std::string& RuntimeConfig::getSandboxPath() const {
    return sandboxPath;
}

std::string RuntimeConfig::getMinecraftLibraryPath() const {
    return nativeLibraryDir + "/libminecraftpe.so";
}

}
