package svd.joggingtimer.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.FloatingActionButton
import android.view.View
import kotlinx.android.synthetic.main.activity_base.*
import svd.joggingtimer.R

open class BaseActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base)
        setSupportActionBar(toolbar)
    }
    override fun setContentView(@LayoutRes layoutRes: Int){
        layoutInflater.inflate(layoutRes, findViewById(R.id.container))
    }


    fun enableFab(): FloatingActionButton {
        fab.visibility = View.VISIBLE
        return fab
    }
}
