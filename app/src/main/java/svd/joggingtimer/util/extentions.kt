package svd.joggingtimer.util

import android.app.PendingIntent
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import java.util.concurrent.TimeUnit


/**
 * Created by Sean on 5-5-2018.
 */

fun from_HHMMSS_ToLong(hours: Int, minutes: Int, seconds: Int): Long {
    return (hours*3600 + minutes * 60 + seconds).toLong() *1000
}

//The +999 is applied so that the timer hits 0 exactly when it's done
fun Long.toHHMMSS(): String {
    val time = this+999
    return String.format("%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(time),
            TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1))
}


fun <T : Any?> NotificationCompat.Builder.addButton(context: Context, clazz: Class<T>, action: String, title:String = "Placeholder", drawableRef: Int = android.R.drawable.ic_media_play){
    val intent = Intent(context, clazz).apply {
        this.action = action
    }
    val pendingIntent = PendingIntent.getService(context, 0, intent, 0)
    addAction(NotificationCompat.Action(drawableRef, title, pendingIntent))
}

fun <T>LiveData<T>.observeOnce(observer: Observer<T>){
    observeForever {
        observer.onChanged(it)
        removeObserver(observer)
    }
}
