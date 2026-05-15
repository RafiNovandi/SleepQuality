package com.muhammadrafinovandi0108.sleepquality.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammadrafinovandi0108.sleepquality.database.DataTidurDao
import com.muhammadrafinovandi0108.sleepquality.model.DataTidur
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


class MainViewModel(dao: DataTidurDao) : ViewModel() {
    val data: StateFlow<List<DataTidur>> = dao.getDataTidur().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )
    fun getDataTidur(id: Long): DataTidur? {
        return data.value.find { it.id == id }
    }
}