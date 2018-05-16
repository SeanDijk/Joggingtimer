package svd.joggingtimer.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_base.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.*
import svd.joggingtimer.R
import svd.joggingtimer.recyclerview.TimerModelRecyclerViewAdapter
import svd.joggingtimer.TimerModelRepository

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


        timerRecyclerView.layoutManager = LinearLayoutManager(this)
        timerRecyclerView.adapter = TimerModelRecyclerViewAdapter(TimerModelRepository.models)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
