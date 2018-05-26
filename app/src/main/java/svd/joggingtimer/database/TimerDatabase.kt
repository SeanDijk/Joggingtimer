package svd.joggingtimer.database

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.arch.persistence.room.*
import android.content.Context
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import svd.joggingtimer.domain.TimerModel

/**
 * Created by Sean on 22-5-2018.
 */
@Database(entities = [(TimerModel::class)], version = 1, exportSchema = false)
abstract class TimerDatabase : RoomDatabase() {
    abstract fun timerModelDao(): TimerModelDao

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearAllTables() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun execute(action: (it: TimerDatabase) -> Unit): Job {
        return launch{
            action(this@TimerDatabase)
        }
    }


    companion object {
        private var INSTANCE: TimerDatabase? = null

        fun getInstance(context: Context): TimerDatabase {
            if (INSTANCE == null) {
                synchronized(TimerDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            TimerDatabase::class.java, "weather.db")
                            .build()
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}