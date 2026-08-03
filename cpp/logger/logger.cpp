
#include "logger.h"
#include <android/log.h>

namespace bedrock::logger {

void Logger::init() {
    __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "Logger initialized - سجلات Native باستخدام Android Log");
}

void Logger::log(int level, const std::string& tag, const std::string& msg) {
    android_LogPriority prio = ANDROID_LOG_INFO;
    if (level == 0) prio = ANDROID_LOG_DEBUG;
    else if (level == 2) prio = ANDROID_LOG_ERROR;
    __android_log_print(prio, tag.c_str(), "%s", msg.c_str());
}

void Logger::i(const std::string& tag, const std::string& msg) {
    __android_log_print(ANDROID_LOG_INFO, tag.c_str(), "%s", msg.c_str());
}

void Logger::e(const std::string& tag, const std::string& msg) {
    __android_log_print(ANDROID_LOG_ERROR, tag.c_str(), "%s", msg.c_str());
}

} // namespace
