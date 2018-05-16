package svd.joggingtimer.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi

/**
 * Created by Sean on 10-5-2018.
 */
object NotificationChannelHandler {
    const val CHANNEL_TIMER_NAME = "Jogging Timer"
    const val CHANNEL_TIMER_ID = "jogging_timer"


    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannels(context: Context){
        createChannel(context, NotificationManager.IMPORTANCE_LOW, CHANNEL_TIMER_NAME, CHANNEL_TIMER_ID)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(context: Context, importance: Int, channelName: String, channelId: String){
        val notificationChannel = NotificationChannel(channelId, channelName, importance)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}