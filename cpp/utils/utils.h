
#pragma once
#include <string>

namespace bedrock::utils {

std::string getAbi();
std::string getDeviceModel();
std::string readFile(const std::string& path);
bool writeFile(const std::string& path, const std::string& content);

} // namespace
