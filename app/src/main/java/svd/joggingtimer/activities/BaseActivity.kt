package svd.joggingtimer.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.FloatingActionButton
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_base.*
import svd.joggingtimer.R
import svd.joggingtimer.activities.settings.SettingsActivity

open class BaseActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base)
        setSupportActionBar(toolbar)
        enableUpButton()
    }
    override fun setContentView(@LayoutRes layoutRes: Int){
        layoutInflater.inflate(layoutRes, findViewById(R.id.container))
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
            android.R.id.home -> { onBackPressed(); true }
            R.id.action_settings -> { startActivity(SettingsActivity.createIntent(this)); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun enableFab(enable: Boolean = true): FloatingActionButton {
        if(enable)
            fab.visibility = View.VISIBLE
        else
            fab.visibility = View.GONE
        return fab
    }

    fun enableUpButton(enable: Boolean = true){
        supportActionBar?.setDisplayShowHomeEnabled(enable)
        supportActionBar?.setDisplayHomeAsUpEnabled(enable)
    }



}
