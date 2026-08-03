
package com.bedrock.client.analytics
import android.content.Context

class AnalyticsManager private constructor(private val context: Context) {
    fun logEvent(name: String, params: Map<String, String> = emptyMap()) { /* Firebase or custom */ }

    companion object {
        @Volatile private var INSTANCE: AnalyticsManager? = null
        fun getInstance(ctx: Context) = INSTANCE ?: synchronized(this) { INSTANCE ?: AnalyticsManager(ctx).also { INSTANCE = it } }
    }
}
