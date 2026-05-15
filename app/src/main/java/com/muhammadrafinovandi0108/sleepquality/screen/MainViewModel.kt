package com.muhammadrafinovandi0108.sleepquality.screen

import androidx.lifecycle.ViewModel
import com.muhammadrafinovandi0108.sleepquality.model.DataTidur
import com.muhammadrafinovandi0108.sleepquality.R

class MainViewModel : ViewModel() {

    val data = listOf(
        DataTidur(
            id = 1,
            tanggal = "2026-05-10 22:00:00",
            jamTidur = "22:00",
            jamBangun = "06:00",
            kategori = "Remaja",
            durasi = 8 * 60,
            status = R.string.ideal
        ),

        DataTidur(
            id = 2,
            tanggal = "2026-05-11 23:30:00",
            jamTidur = "23:30",
            jamBangun = "05:30",
            kategori = "Anak",
            durasi = 6 * 60,
            status = R.string.ideal
        ),

        DataTidur(
            id = 3,
            tanggal = "2026-05-12 21:00:00",
            jamTidur = "21:00",
            jamBangun = "06:00",
            kategori = "Dewasa",
            durasi = 9 * 60,
            status = R.string.ideal
        ),
        DataTidur(
            id = 4,
            tanggal = "2026-05-10 22:00:00",
            jamTidur = "22:00",
            jamBangun = "06:36",
            kategori = "Dewasa",
            durasi = 8 * 60,
            status = R.string.ideal
        ),

        DataTidur(
            id = 5,
            tanggal = "2026-05-11 23:30:00",
            jamTidur = "23:30",
            jamBangun = "05:30",
            kategori = "Dewasa",
            durasi = 6 * 60,
            status = R.string.kurang
        ),

        DataTidur(
            id = 6,
            tanggal = "2026-05-12 21:00:00",
            jamTidur = "21:00",
            jamBangun = "06:00",
            kategori = "Dewasa",
            durasi = 9 * 60,
            status = R.string.ideal
        ),
        DataTidur(
            id = 7,
            tanggal = "2026-05-10 22:00:00",
            jamTidur = "22:00",
            jamBangun = "06:00",
            kategori = "Anak",
            durasi = 8 * 60,
            status = R.string.ideal
        ),

        DataTidur(
            id = 8,
            tanggal = "2026-05-11 23:30:00",
            jamTidur = "23:30",
            jamBangun = "05:10",
            kategori = "Remaja",
            durasi = 6 * 60,
            status = R.string.kurang
        ),

        DataTidur(
            id = 9,
            tanggal = "2026-05-12 21:00:00",
            jamTidur = "21:00",
            jamBangun = "06:20",
            kategori = "Anak",
            durasi = 9 * 60,
            status = R.string.ideal
        )
    )
    fun getDataTidur(id: Long): DataTidur? {
        return data.find { it.id == id }
    }
}