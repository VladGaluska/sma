package ro.upt.sma.context.activity

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityRecognitionClient

class ActivityRecognitionHandler(context: Context) {

    private val client: ActivityRecognitionClient = ActivityRecognition.getClient(context)

    fun registerPendingIntent(): PendingIntent {

        val intent = Intent(ActivityRecognitionService::class.java);

        val pendingIntent = PendingIntent(intent)

        client.requestActivityUpdates(1, pendingIntent)

        return pendingIntent

    }

    fun unregisterPendingIntent(pendingIntent: PendingIntent) {
        client.removeActivityUpdates(pendingIntent)
    }

}
