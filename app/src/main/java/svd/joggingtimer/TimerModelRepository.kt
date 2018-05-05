package svd.joggingtimer

import svd.joggingtimer.model.TimerModel

/**
 * Created by Sean on 5-5-2018.
 */
object TimerModelRepository {

    val models = arrayListOf(
            TimerModel("Timer 1", 3_600_000, 3600),
            TimerModel("Timer 2", 100, 200),
            TimerModel("Timer 3", 100, 200),
            TimerModel("Timer 4", 100, 200)
    )


}