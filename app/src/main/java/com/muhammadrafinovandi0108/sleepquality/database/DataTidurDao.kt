package com.muhammadrafinovandi0108.sleepquality.database

import androidx.room.Dao
import androidx.room.Delete
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

    @Query("SELECT * FROM datatidur WHERE isDeleted = 0 ORDER BY tanggal DESC")
    fun getActiveData(): Flow<List<DataTidur>>

    @Query("SELECT * FROM datatidur WHERE isDeleted = 1 ORDER BY tanggal DESC")
    fun getTrashData(): Flow<List<DataTidur>>

    @Query("SELECT * FROM datatidur WHERE id = :id")
    suspend fun getById(id: Long): DataTidur?

    @Query("DELETE FROM datatidur WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE datatidur SET isDeleted = 1 WHERE id = :id")
    suspend fun moveToTrash(id: Long)

    @Query("UPDATE datatidur SET isDeleted = 0 WHERE id = :id")
    suspend fun restoreData(id: Long)

    @Query("DELETE FROM datatidur WHERE id = :id")
    suspend fun deletePermanent(id: Long)

    @Delete
    suspend fun delete(data: DataTidur)
}