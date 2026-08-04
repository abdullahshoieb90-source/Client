Enter#pragma once

#include <string>

namespace bedrock::runtime {

class RuntimeConfig {
public:
    static RuntimeConfig& getInstance();

    void setMinecraftPackage(const std::string& packageName);
    void setApkPath(const std::string& path);
    void setNativeLibraryDir(const std::string& path);
    void setSandboxPath(const std::string& path);

    const std::string& getMinecraftPackage() const;
    const std::string& getApkPath() const;
    const std::string& getNativeLibraryDir() const;
    const std::string& getSandboxPath() const;

    std::string getMinecraftLibraryPath() const;

private:
    RuntimeConfig() = default;

    std::string minecraftPackage;
    std::string apkPath;
    std::string nativeLibraryDir;
    std::string sandboxPath;
};

}
