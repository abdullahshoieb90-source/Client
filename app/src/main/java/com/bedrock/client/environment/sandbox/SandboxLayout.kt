package com.bedrock.client.environment.sandbox

import java.io.File

data class SandboxLayout(
    val root: File,
    val gamesDir: File,
    val comMojangDir: File,
    val minecraftPeDir: File,
    val worldsDir: File,
    val resourcePacksDir: File,
    val behaviorPacksDir: File,
    val skinPacksDir: File,
    val developmentResourcePacksDir: File,
    val developmentBehaviorPacksDir: File,
    val logsDir: File,
    val metadataDir: File,
    val optionsFile: File,
    val globalResourcePacksFile: File,
    val validKnownPacksFile: File,
    val instanceStateFile: File
)
