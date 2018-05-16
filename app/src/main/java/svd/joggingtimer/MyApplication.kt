package svd.joggingtimer

import android.app.Application
import android.os.Build
import svd.joggingtimer.util.NotificationChannelHandler

/**
 * Created by Sean on 10-5-2018.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannelHandler.createChannels(this)
        }
    }
}