
package com.bedrock.client.service
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.bedrock.client.modules.ModuleManager

class OverlayService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null
    override fun onCreate() {
        super.onCreate()
        // إنشاء overlay window لـ FPS/CPS
    }
    override fun onDestroy() { super.onDestroy() }
}
