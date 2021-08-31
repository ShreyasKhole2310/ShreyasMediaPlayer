package com.example.shreyasencoratest.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.shreyasencoratest.database.SongEntity

class SongDetailViewModel(application: Application) : AndroidViewModel(application) {
    val itemSong = MutableLiveData<SongEntity>()
}