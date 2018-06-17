package svd.joggingtimer.activities

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_base.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.async
import svd.joggingtimer.R
import svd.joggingtimer.recyclerview.TimerModelRecyclerViewAdapter
import svd.joggingtimer.TimerModelRepository
import svd.joggingtimer.recyclerview.TouchHelper
import svd.joggingtimer.util.observeOnce

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enableFab().apply {
            setImageResource(R.drawable.ic_add_white_24dp)
            setOnClickListener { view ->
                startActivity(CreateTimerActivity.createIntent(this@MainActivity))
            }
        }
        enableUpButton(false)

        timerRecyclerView.layoutManager = LinearLayoutManager(this)


        timerRecyclerView.adapter = TimerModelRecyclerViewAdapter()
        ItemTouchHelper(TouchHelper(timerRecyclerView.adapter as TouchHelper.ActionCompletionContract)).attachToRecyclerView(timerRecyclerView)

    }

    override fun onResume() {
        super.onResume()
        TimerModelRepository.getModels(this).observeOnce( Observer {
            (timerRecyclerView.adapter as TimerModelRecyclerViewAdapter).setData(it!!.toMutableList())
        })
    }

}
