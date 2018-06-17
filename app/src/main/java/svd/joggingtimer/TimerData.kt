package svd.joggingtimer

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import svd.joggingtimer.domain.JoggingTimer
import svd.joggingtimer.domain.TimerModel
import svd.joggingtimer.util.toHHMMSS

/**
 * Created by Sean on 11-5-2018.
 */
object TimerData {
    var model: TimerModel? = null
    set(value){
        field = value
        state.value = JoggingTimer.State.JOG
    }

    var state = MutableLiveData<JoggingTimer.State>().apply { value = JoggingTimer.State.JOG }
    var paused = MutableLiveData<Boolean>().apply { value = false }



    private var timeLeftString = MutableLiveData<String>().apply { value = "00:00:00" }
    var timeLeft= MutableLiveData<Long>().apply {
        value = 0
        observeForever {
            it?.let {
                val string = it.toHHMMSS()
                if(string != timeLeftString.value)
                    timeLeftString.value = string
            }
        }
    }

    fun getTimeLeftAsStingLifeData() : LiveData<String> = timeLeftString
}