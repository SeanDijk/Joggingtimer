package svd.joggingtimer

import svd.joggingtimer.domain.TimerModel

/**
 * Created by Sean on 5-5-2018.
 */
object TimerModelRepository {

    val models = arrayListOf(
            TimerModel("Timer 1", 5000, 5000),
            TimerModel("Timer 2", 5000, 5000),
            TimerModel("Timer 3", 5000, 5000),
            TimerModel("Timer 4", 5000, 5000)
    )


}