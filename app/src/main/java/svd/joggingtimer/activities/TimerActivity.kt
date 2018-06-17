package svd.joggingtimer.activities

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_timer.*
import svd.joggingtimer.R
import svd.joggingtimer.TimerData
import svd.joggingtimer.domain.JoggingTimer
import svd.joggingtimer.domain.TimerModel
import svd.joggingtimer.services.TimerService
import svd.joggingtimer.util.progresscircle.CircularSectionCircle
import svd.joggingtimer.util.toHHMMSS
import java.util.*


class TimerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        //Get the TimerModel
        intent.getParcelableExtra<TimerModel>(ARG_TIMER)?.let {
            TimerData.model = it
            //TimerService.stopTimer(this)
            startService(TimerService.getIntent(this, it))
        }

        initViews()

        //Set the params of the Timer Circle.
        timerCircle.setParams(listOf(
                CircularSectionCircle.CircleParam(Color.YELLOW, TimerData.model?.joggingDuration!!.toInt()),
                CircularSectionCircle.CircleParam(Color.BLUE, TimerData.model?.restDuration!!.toInt())
        ))


        //Observer to update the Timer strings. Checks the state, so that the correct string is set.
        TimerData.getTimeLeftAsStingLifeData().observe(this, Observer {
            when(TimerData.state.value) {
                JoggingTimer.State.JOG -> {
                    jogTimerTimeTextView.text = it!!
                    restTimerTimeTextView.text = TimerData.model?.restDuration?.toHHMMSS()
                }
                JoggingTimer.State.REST -> {
                    restTimerTimeTextView.text = it!!
                    jogTimerTimeTextView.text = TimerData.model?.joggingDuration?.toHHMMSS()
                }
            }
        })

        //Observes if the timer is paused to change the button string
        TimerData.paused.observe(this, Observer {
            if(it!!)
                pauseResumeButton.setText(R.string.button_resume)
            else
                pauseResumeButton.setText(R.string.button_pause)
        })
        TimerData.state.observe(this, Observer {
            if(it == JoggingTimer.State.STOPPED)
                pauseResumeButton.setText(R.string.button_start)
        })

        //Update the progress bar
        TimerData.timeLeft.observe(this, Observer {
            if(TimerData.state.value == JoggingTimer.State.JOG)
                timerCircle.setOffsetWeight((TimerData.model!!.joggingDuration -it!!).toFloat())
            else if(TimerData.state.value == JoggingTimer.State.REST)
                timerCircle.setOffsetWeight((TimerData.model!!.joggingDuration + (TimerData.model!!.restDuration - it!!)).toFloat())
        })

        //Sets the button actions
        pauseResumeButton.setOnClickListener {
            if(TimerData.state.value == JoggingTimer.State.STOPPED) {
                startService(TimerService.getIntent(this, TimerData.model!!))
                pauseResumeButton.setText(R.string.button_pause)
            }
            else {
                TimerService.toggleTimer(this)
            }
        }

        stopButton.setOnClickListener {
            TimerService.stopTimer(this)
            initViews()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(isFinishing){
            TimerService.stopTimer(this)
        }
    }

    private fun initViews(){
        //Set the Jog and Rest Timer strings to the start values
        jogTimerTimeTextView.text = TimerData.model?.joggingDuration?.toHHMMSS()
        restTimerTimeTextView.text = TimerData.model?.restDuration?.toHHMMSS()
        timerCircle.setOffsetWeight(0f)
    }

    companion object {
        private const val ARG_TIMER = "timer"

        fun createIntent(context: Context, timerModel: TimerModel): Intent {
            return Intent(context, TimerActivity::class.java).apply {
                putExtra(ARG_TIMER, timerModel)
            }
        }
    }


}
