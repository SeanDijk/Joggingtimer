package svd.joggingtimer.util.actionhandler

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class VibrationHandler(context: Context, checker: SharedPrefHandlerChecker, private val duration: Long): ActionHandler(checker) {
     private val vibrator: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        context.getSystemService(Vibrator::class.java)
    } else {
         context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
    private val vibrationEffect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)
    } else {
        null
    }


    override fun performActionImpl() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(vibrationEffect)
        } else {
            vibrator.vibrate(duration)
        }
    }

    override fun stopActionImpl() {
       vibrator.cancel()
    }

}
