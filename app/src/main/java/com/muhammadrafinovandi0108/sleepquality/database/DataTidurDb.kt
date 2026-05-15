package com.muhammadrafinovandi0108.sleepquality.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.muhammadrafinovandi0108.sleepquality.model.DataTidur

@Database(entities = [DataTidur::class], version = 1, exportSchema = false)
abstract class DataTidurDb : RoomDatabase() {
    abstract val dao: DataTidurDao

    companion object {
        @Volatile
        private  var INSTANCE: DataTidurDb? = null

        fun getInstance(context: Context): DataTidurDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataTidurDb::class.java,
                        "datatidur.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
