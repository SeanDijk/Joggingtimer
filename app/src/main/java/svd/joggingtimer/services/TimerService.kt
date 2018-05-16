package svd.joggingtimer.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import svd.joggingtimer.domain.TimerModel
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import svd.joggingtimer.*
import svd.joggingtimer.activities.TimerActivity
import svd.joggingtimer.domain.JoggingTimer
import svd.joggingtimer.util.NotificationChannelHandler
import svd.joggingtimer.util.addButton
import svd.joggingtimer.util.toHHMMSS


class TimerService : Service() {
    private var notificationBuilder : NotificationCompat.Builder? = null
    private lateinit var model: TimerModel
    var timer: JoggingTimer? = null
    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            if(it.extras!= null && it.extras.getParcelable<TimerModel>(ARG_TIMER_MODEL)!= null) {
                model = it.extras.getParcelable(ARG_TIMER_MODEL)
                TimerData.model = model
            }
            val action = it.action
            when (action) {
                ACTION_START_SERVICE -> start()
                ACTION_STOP_SERVICE -> stop()
                ACTION_PAUSE -> pause()
                ACTION_RESUME -> resume()
                ACTION_TOGGLE_PAUSE -> toggle()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(){
        println("started")
        startForegroundService(applicationContext)
        timer = JoggingTimer(model)

        TimerData.getTimeLeftAsStingLifeData().observeForever {
            println("Observer: $it")
            when(timer!!.state) {
                JoggingTimer.State.JOG -> setNotifcationText(it!! , model.restDuration.toHHMMSS())
                JoggingTimer.State.REST -> setNotifcationText(model.joggingDuration.toHHMMSS(), it!!)
            }
        }

        TimerData.paused.observeForever {
            if(it!!)
                notificationBuilder?.mActions?.get(0)?.title = getString(R.string.button_resume) //todo add to strings file
            else
                notificationBuilder?.mActions?.get(0)?.title = getString(R.string.button_pause) //todo add to strings file
            notifyNotification()
        }

        timer?.start()

    }

    private fun resume(){
        println("Resumed")
        timer?.resume()
    }
    private fun pause(){
        println("Paused")
        timer?.pause()
    }
    private fun stop(){
        println("Stopped")
        timer?.stop()
        stopForegroundService()
    }
    private fun toggle(){
        println("Toggle")
        timer?.toggle()
    }


    private fun startForegroundService(context: Context) {

        notificationBuilder = NotificationCompat.Builder(this, NotificationChannelHandler.CHANNEL_TIMER_ID).apply {
            // Create notification default intent.
            val intent = Intent(context, TimerActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this@TimerService, 0, intent, 0)
            // Make notification show big text.
//        val bigTextStyle = NotificationCompat.BigTextStyle()
//        bigTextStyle.setBigContentTitle("Placeholder")
//        bigTextStyle.bigText("Placeholder 2")
//        setStyle(bigTextStyle)
            setWhen(System.currentTimeMillis())
            setSmallIcon(R.mipmap.ic_launcher)
            setLargeIcon( BitmapFactory.decodeResource(resources, R.drawable.ic_delete_black_24dp))

            // Make the notification max priority.
            priority = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                NotificationManager.IMPORTANCE_HIGH
            } else
                Notification.PRIORITY_MAX

            // Make head-up notification.
            setFullScreenIntent(pendingIntent, true)


            //Add pause/resume button

            addButton(this@TimerService, TimerService::class.java, ACTION_TOGGLE_PAUSE, getString(R.string.button_pause)) //todo icon?
            addButton(this@TimerService, TimerService::class.java, ACTION_STOP_SERVICE, getString(R.string.button_stop)) //todo icon?


        }
        // Create notification builder.
        val notification = notificationBuilder!!.build()

        // Start foreground service.
        startForeground(NOTIFICATION_ID, notification)
    }
    private fun stopForegroundService() {

        // Stop foreground service and remove the notification.
        stopForeground(true)

        // Stop the foreground service.
        stopSelf()
    }

    private fun setNotifcationText(jogTime: String, restTime: String){
        notificationBuilder?.setContentTitle("Jog time: $jogTime\nRest time: $restTime")
        notifyNotification()
    }
    private fun notifyNotification(){
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(NOTIFICATION_ID, notificationBuilder?.build())
    }




    companion object {
        const val ACTION_START_SERVICE = "svd.joggingtimer.services.action.START_SERVICE"
        const val ACTION_RESUME = "svd.joggingtimer.services.action.RESUME"
        const val ACTION_PAUSE = "svd.joggingtimer.services.action.PAUSE"
        const val ACTION_TOGGLE_PAUSE = "svd.joggingtimer.services.action.TOGGLE_PAUSE"
        const val ACTION_STOP_SERVICE = "svd.joggingtimer.services.action.STOP_SERVICE"

        private const val ARG_TIMER_MODEL = "svd.joggingtimer.services.extra.TIMER_MODEL"

        private const val NOTIFICATION_ID = 666

        fun getIntent(context: Context, timerModel: TimerModel): Intent{
            return Intent(context, TimerService::class.java).apply {
                putExtra(ARG_TIMER_MODEL, timerModel)
                action = ACTION_START_SERVICE
            }

        }

        fun resumeTimer(context: Context) {
            startAction(context, ACTION_RESUME)
        }
        fun pauseTimer(context: Context) {
            startAction(context, ACTION_PAUSE)
        }
        fun toggleTimer(context: Context) {
            startAction(context, ACTION_TOGGLE_PAUSE)
        }
        fun stopTimer(context: Context){
            startAction(context, ACTION_STOP_SERVICE)
        }


        private inline fun startAction(context: Context, action: String, editor: (intent: Intent) -> Unit ={}){
            val intent = Intent(context, TimerService::class.java)
            intent.action = action
            intent.putExtra("This is a fix for extras being null while not supposed to", ' ')
            editor(intent)
            context.startService(intent)
        }


    }
}
