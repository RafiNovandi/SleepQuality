package com.muhammadrafinovandi0108.sleepquality.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "datatidur")
data class DataTidur(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val tanggal: String,
    val jamTidur: String,
    val jamBangun: String,
    val kategori: String,
    val durasi: Int,
    val status: Int,
    var isDeleted: Int = 0
)