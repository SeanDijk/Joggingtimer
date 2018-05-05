package svd.joggingtimer.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_timer.*
import svd.joggingtimer.R
import svd.joggingtimer.model.TimerModel
import svd.joggingtimer.toHHMMSS
import kotlin.concurrent.timer

class TimerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        val model = intent.getParcelableExtra<TimerModel>(ARG_TIMER)

        jogTimerTimeTextView.text = model.joggingDuration.toHHMMSS()
        restTimerTimeTitle.text = model.restDuration.toHHMMSS()
    }

    companion object {
        private const val ARG_TIMER = "timer"

        fun getIntent(context: Context, timerModel: TimerModel): Intent {
            return Intent(context, TimerActivity::class.java).apply {
                putExtra(ARG_TIMER, timerModel)
            }
        }
    }
}
