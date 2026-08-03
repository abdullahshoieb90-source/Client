
package com.bedrock.client.updater
import com.bedrock.client.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Updater(private val api: ApiService = ApiService.create()) {
    suspend fun checkForUpdate(): UpdateInfo? = withContext(Dispatchers.IO) { null }
    data class UpdateInfo(val version: String, val url: String, val changelog: String)
}
