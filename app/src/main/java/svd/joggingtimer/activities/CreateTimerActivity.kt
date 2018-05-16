package svd.joggingtimer.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import svd.joggingtimer.R
import svd.joggingtimer.domain.TimerModel

class CreateTimerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_timer)
        enableFab().apply {
            setImageResource(R.drawable.ic_check_white_24dp)
            setOnClickListener {
                TimerActivity.createIntent(this@CreateTimerActivity, TimerModel("", 0, 0)) //todo replace with actual timer
            }
        }




    }

    companion object {
        fun createIntent(context: Context) = Intent(context, CreateTimerActivity::class.java)
    }
}
