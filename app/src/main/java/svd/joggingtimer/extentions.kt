package svd.joggingtimer

import java.util.concurrent.TimeUnit


/**
 * Created by Sean on 5-5-2018.
 */
fun Long.toHHMMSS()= String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(this),
            TimeUnit.MILLISECONDS.toMinutes(this) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(this) % TimeUnit.MINUTES.toSeconds(1))
