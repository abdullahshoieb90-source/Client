
package com.bedrock.client.network
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("versions.json")
    suspend fun getVersions(): List<VersionDto>

    data class VersionDto(val id: String, val supported: Boolean, val url: String)

    companion object {
        fun create(): ApiService = Retrofit.Builder()
            .baseUrl("https://api.bedrockclient.example/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }
}
