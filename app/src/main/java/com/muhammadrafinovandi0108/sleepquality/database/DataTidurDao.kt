package com.muhammadrafinovandi0108.sleepquality.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.muhammadrafinovandi0108.sleepquality.model.DataTidur
import kotlinx.coroutines.flow.Flow

@Dao
interface DataTidurDao {
    @Insert
    suspend fun insert(datatidur: DataTidur)

    @Update
    suspend fun update(datatidur: DataTidur)

    @Query("SELECT * FROM datatidur ORDER BY tanggal DESC")
    fun getDataTidur(): Flow<DataTidur>
}