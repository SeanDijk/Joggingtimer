package svd.joggingtimer

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import svd.joggingtimer.database.TimerDatabase
//import svd.joggingtimer.database.TimerDatabase
import svd.joggingtimer.domain.TimerModel

/**
 * Created by Sean on 5-5-2018.
 */
object TimerModelRepository {

//    val models = arrayListOf(
//            TimerModel("Timer 1", 5000, 5000),
//            TimerModel("Timer 2", 5000, 5000),
//            TimerModel("Timer 3", 5000, 5000),
//            TimerModel("Timer 4", 5000, 5000)
//    )

    fun getModels(context: Context): LiveData<List<TimerModel>> = TimerDatabase.getInstance(context).timerModelDao().getAll()
    //TimerDatabase.getInstance(context)!!.timerModelDao().getAll() //todo check if toMutatble list is needed


}