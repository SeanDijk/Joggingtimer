package svd.joggingtimer.domain

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Sean on 5-5-2018.
 */
@Parcelize
@Entity(
        tableName = "timer",
        indices = [(Index(value = ["order_number"], unique = true))]
)
data class TimerModel(
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null,
        @ColumnInfo(name = "name")
        var name: String,
        @ColumnInfo(name = "jog_duration")
        var joggingDuration: Long,
        @ColumnInfo(name = "rest_duration")
        var restDuration : Long,
        @ColumnInfo(name = "order_number")
        var orderNumber: Long = -1
) : Parcelable{
    //todo compare function to use ordernumber?
}