package svd.joggingtimer.util

import android.view.View

/**
 * Created by Sean on 21-5-2018.
 */
abstract class OnSingleClickListener: View.OnClickListener {
    private var clicked = false

    override fun onClick(v: View?) {
        if (!clicked){
            onSingleClick(v)
            clicked = true
        }
    }

    abstract fun onSingleClick(v: View?)
}