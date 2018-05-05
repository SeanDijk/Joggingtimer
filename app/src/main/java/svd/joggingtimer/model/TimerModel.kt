package svd.joggingtimer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Sean on 5-5-2018.
 */
@Parcelize
data class TimerModel(val name: String, val joggingDuration: Long, val restDuration : Long) : Parcelable