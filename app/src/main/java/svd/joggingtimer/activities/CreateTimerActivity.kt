package svd.joggingtimer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import kotlinx.android.synthetic.main.activity_create_timer.*
import svd.joggingtimer.R
import svd.joggingtimer.database.TimerDatabase
import svd.joggingtimer.database.insertNew
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

        val editTimer: TimerModel? = intent.getParcelableExtra(ARG_TIMER_TO_EDIT)
        editTimer?.let {
            nameInput.setText(editTimer.name)
            jogDuration = editTimer.joggingDuration
            restDuration = editTimer.restDuration
        }

        enableFab().apply {
            setImageResource(R.drawable.ic_check_white_24dp)
            setOnClickListener {
                val timerModel : TimerModel
                if (editTimer!= null){
                    timerModel = editTimer.apply {
                        name = nameInput.text.toString()
                        this.joggingDuration = jogDuration
                        this.restDuration = this@CreateTimerActivity.restDuration
                    }
                    TimerDatabase.getInstance(this@CreateTimerActivity).execute {
                        it.timerModelDao().insert(timerModel)
                    }
                } else{
                    timerModel = TimerModel( name =  nameInput.text.toString(), joggingDuration = jogDuration, restDuration = restDuration)
                    TimerDatabase.getInstance(this@CreateTimerActivity).execute {
                        it.timerModelDao().insertNew(timerModel)
                    }
                }

                startActivity(TimerActivity.createIntent(this@CreateTimerActivity, timerModel))
            }
        }


        jogTimerInput.setOnClickListener {
            TimerPickerFragment.newInstance(JOG_TAG, jogDuration).show(supportFragmentManager, JOG_TAG)
        }
        restTimerInput.setOnClickListener {
            TimerPickerFragment.newInstance(REST_TAG, restDuration).show(supportFragmentManager, REST_TAG)
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

        private const val ARG_TIMER_TO_EDIT = "ARG_TIMER_TO_EDIT"

        fun createIntent(context: Context) = Intent(context, CreateTimerActivity::class.java)
        fun createIntent(context: Context, model: TimerModel): Intent {
            return createIntent(context).apply {
                putExtra(ARG_TIMER_TO_EDIT, model)
            }
        }
    }
}
