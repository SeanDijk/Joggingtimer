package svd.joggingtimer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import kotlinx.android.synthetic.main.activity_create_timer.*
import svd.joggingtimer.R
import svd.joggingtimer.domain.TimerModel
import svd.joggingtimer.fragments.TimerPickerFragment
import svd.joggingtimer.util.toHHMMSS

class CreateTimerActivity : BaseActivity(), TimerPickerFragment.OnTimerTimeConfirmedListener {
    private var jogDuration =0L
        set(value) {
            field = value
            jogTimerInput.setText(value.toHHMMSS())
        }
    private var restDuration = 0L
        set(value) {
            field = value
            restTimerInput.setText(value.toHHMMSS())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_timer)
        enableFab().apply {
            setImageResource(R.drawable.ic_check_white_24dp)
            setOnClickListener {
                val timermodel = TimerModel(nameInput.text.toString(), jogDuration, restDuration)
                //todo save timer
                startActivity(TimerActivity.createIntent(this@CreateTimerActivity, timermodel))
            }
        }


        jogTimerInput.setOnClickListener {
            TimerPickerFragment.newInstance(JOG_TAG).show(supportFragmentManager, JOG_TAG)
        }
        restTimerInput.setOnClickListener {
            TimerPickerFragment.newInstance(REST_TAG).show(supportFragmentManager, REST_TAG)
        }

    }

    override fun onTimeConfirmed(long: Long, tag: String) {
        when(tag){
            JOG_TAG -> jogDuration = long
            REST_TAG -> restDuration = long
        }
    }


    companion object {
        private const val JOG_TAG = "jog"
        private const val REST_TAG = "rest"

        fun createIntent(context: Context) = Intent(context, CreateTimerActivity::class.java)
    }
}
