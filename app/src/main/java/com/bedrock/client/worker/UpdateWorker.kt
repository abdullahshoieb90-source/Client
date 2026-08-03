
package com.bedrock.client.worker
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bedrock.client.updater.Updater

class UpdateWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        val updater = Updater()
        updater.checkForUpdate()
        return Result.success()
    }
}
