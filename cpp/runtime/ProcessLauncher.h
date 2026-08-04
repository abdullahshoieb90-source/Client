#pragma once

namespace bedrock::runtime {

class ProcessLauncher {
public:
    static ProcessLauncher& getInstance();

    bool launch();
    void shutdown();

private:
    ProcessLauncher() = default;
    ~ProcessLauncher() = default;

    ProcessLauncher(const ProcessLauncher&) = delete;
    ProcessLauncher& operator=(const ProcessLauncher&) = delete;

private:
    bool launched = false;
};

} // namespace bedrock::runtime
