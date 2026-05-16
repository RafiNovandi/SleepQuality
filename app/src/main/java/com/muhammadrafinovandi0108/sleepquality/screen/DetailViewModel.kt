package com.muhammadrafinovandi0108.sleepquality.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammadrafinovandi0108.sleepquality.database.DataTidurDao
import com.muhammadrafinovandi0108.sleepquality.model.DataTidur
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(private val dao: DataTidurDao) : ViewModel() {
    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)

    fun insert(jamTidur: String, jamBangun: String, kategori: String, durasi: Int, status: Int){
        val data = DataTidur(
            tanggal = formatter.format(Date()),
            jamTidur = jamTidur,
            jamBangun = jamBangun,
            kategori = kategori,
            durasi = durasi,
            status = status
        )
        viewModelScope.launch(Dispatchers.IO){
            dao.insert(data)
        }
    }

    suspend fun getById(id: Long): DataTidur? {
        return dao.getById(id)
    }

    fun update(id: Long, jamTidur: String, jamBangun: String, kategori: String, durasi: Int, status: Int) {
        val data = DataTidur(
            id = id,
            tanggal = formatter.format(Date()),
            jamTidur = jamTidur,
            jamBangun = jamBangun,
            kategori = kategori,
            durasi = durasi,
            status = status
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(data)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.moveToTrash(id)
        }
    }

}