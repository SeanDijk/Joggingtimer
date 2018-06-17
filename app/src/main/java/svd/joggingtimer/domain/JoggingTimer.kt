package svd.joggingtimer.domain

import svd.joggingtimer.util.CountDownTimer
import svd.joggingtimer.TimerData


/**
 * Created by Sean on 11-5-2018.
 */
class JoggingTimer(val timerModel: TimerModel) {
    enum class State {
        JOG, REST, STOPPED
    }

    var state = State.JOG
        private set(value) {//todo use depedency injection for this behaviour
            field = value
            TimerData.state.value = value
        }


    var timer = createTimer(timerModel.joggingDuration)

    private fun createTimer(duration: Long): CountDownTimer {
        return object : CountDownTimer(duration, 100) {
            override fun onTick(millisUntilFinished: Long) {
                TimerData.timeLeft.value = millisUntilFinished //todo use depedency injection for this behaviour
            }

            override fun onFinish() {
                when (state) {
                    State.JOG -> {
                        state = State.REST
                        timer = createTimer(timerModel.restDuration)
                    }
                    State.REST -> {
                        state = State.JOG
                        timer = createTimer(timerModel.restDuration)
                    }
                }
                timer.start()
            }
        }
    }


    fun start() {   timer.start()   }
    fun pause() {
        timer.pause()
        TimerData.paused.value = true //todo use depedency injection for this behaviour
    }
    fun resume(){
        timer.resume()
        TimerData.paused.value = false //todo use depedency injection for this behaviour
    }
    fun stop()  {
        timer.cancel()
        TimerData.state.value = State.STOPPED //todo use depedency injection for this behaviour
    }
    fun toggle(){
        if (timer.isPaused)
            resume()
        else
            pause()
    }

}
