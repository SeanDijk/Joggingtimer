package svd.joggingtimer.activities

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_timer.*
import svd.joggingtimer.R
import svd.joggingtimer.TimerData
import svd.joggingtimer.domain.JoggingTimer
import svd.joggingtimer.domain.TimerModel
import svd.joggingtimer.services.TimerService
import svd.joggingtimer.util.toHHMMSS


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

        //Set the Jog and Rest Timer strings to the start values
        jogTimerTimeTextView.text = TimerData.model?.joggingDuration?.toHHMMSS()
        restTimerTimeTextView.text = TimerData.model?.restDuration?.toHHMMSS()

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

        TimerData.paused.observe(this, Observer {
            if(it!!)
                pauseResumeButton.setText(R.string.button_resume)
            else
                pauseResumeButton.setText(R.string.button_pause)
        })

        //Starts the service.
        //TimerData.model?.let {  }


        //Sets the button actions
        pauseResumeButton.setOnClickListener { TimerService.toggleTimer(this) }
        stopButton.setOnClickListener { TimerService.stopTimer(this) }
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
