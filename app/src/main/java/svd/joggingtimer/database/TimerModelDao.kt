package svd.joggingtimer.database

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.*
import svd.joggingtimer.domain.TimerModel

@Dao
interface TimerModelDao{
    @Query("SELECT * FROM timer")
    fun getAll(): LiveData<List<TimerModel>>

    @Query("SELECT COUNT(*) FROM timer")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(timerModel: TimerModel)

    @Delete
    fun delete(timerModel: TimerModel)




    //fun swapOrderNumber(timerModel: TimerModel, timerModel2: TimerModel)
}
fun TimerModelDao.insertNew(timerModel: TimerModel){
    timerModel.orderNumber = getCount().toLong()
    insert(timerModel)
}
