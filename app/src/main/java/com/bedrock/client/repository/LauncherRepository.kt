
package com.bedrock.client.repository
import com.bedrock.client.downloader.Downloader
import com.bedrock.client.minecraft.version.VersionManager
import com.bedrock.client.network.ApiService

class LauncherRepository(
    private val api: ApiService = ApiService.create(),
    private val downloader: Downloader = Downloader()
) {
    suspend fun launch(instanceId: String): Result<Unit> {
        return try {
            // يجمع البيانات من الإنترنت + DB + ملفات
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun fetchVersions() = api.getVersions()
}
