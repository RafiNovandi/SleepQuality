package com.muhammadrafinovandi0108.sleepquality.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammadrafinovandi0108.sleepquality.database.DataTidurDao
import com.muhammadrafinovandi0108.sleepquality.model.DataTidur
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class MainViewModel(private val dao: DataTidurDao) : ViewModel() {
    val dataTidur: StateFlow<List<DataTidur>> =
        dao.getActiveData().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
    val trashData: StateFlow<List<DataTidur>> =
        dao.getTrashData().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
    fun moveToTrash(id: Long) = viewModelScope.launch {
        dao.moveToTrash(id)
    }
    fun restoreData(id: Long) = viewModelScope.launch {
        dao.restoreData(id)
    }
    fun delete(id: Long) = viewModelScope.launch {
        val data = dao.getById(id)
        if (data != null) {
            dao.delete(data)
        }
    }
    fun getDataTidur(id: Long): DataTidur? {
        return dataTidur.value.find { it.id == id }
    }
}