package com.muhammadrafinovandi0108.sleepquality.model

data class DataTidur(
    val id: Long,
    val tanggal: String,
    val jamTidur: String,
    val jamBangun: String,
    val kategori: String,
    val durasi: Int,
    val status: Int
)