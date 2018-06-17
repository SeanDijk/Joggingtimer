package svd.joggingtimer.util.actionhandler

import android.content.Context
import android.preference.PreferenceManager

class SharedPrefHandlerChecker(context: Context, private val prefName: String): HandlerChecker{
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun shouldPerformAction(): Boolean {
        return sharedPreferences.getBoolean(prefName, true)
    }

}