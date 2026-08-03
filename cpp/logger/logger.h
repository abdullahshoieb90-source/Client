
#pragma once
#include <string>
#include <android/log.h>

#define LOG_TAG "BedrockClient"

#define LOGI(tag, ...) __android_log_print(ANDROID_LOG_INFO, tag, __VA_ARGS__)
#define LOGE(tag, ...) __android_log_print(ANDROID_LOG_ERROR, tag, __VA_ARGS__)
#define LOGD(tag, ...) __android_log_print(ANDROID_LOG_DEBUG, tag, __VA_ARGS__)

namespace bedrock::logger {

class Logger {
public:
    static void init();
    static void log(int level, const std::string& tag, const std::string& msg);
    static void i(const std::string& tag, const std::string& msg);
    static void e(const std::string& tag, const std::string& msg);
};

} // namespace
