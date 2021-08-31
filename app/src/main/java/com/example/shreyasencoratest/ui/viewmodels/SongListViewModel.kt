package com.example.shreyasencoratest.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shreyasencoratest.api.ApiResultStatus
import com.example.shreyasencoratest.data.DataSource
import com.example.shreyasencoratest.database.SongEntity
import com.example.shreyasencoratest.database.SongRoomDatabase
import com.example.shreyasencoratest.database.mapDto
import com.example.shreyasencoratest.model.SongListRepo
import com.example.shreyasencoratest.ui.fragment.DataUpdates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SongListViewModel(application: Application) : AndroidViewModel(application) {

    var mutableSongList = MutableLiveData<List<SongEntity>>()

    private val appDatabase = SongRoomDatabase.getDatabase(application, viewModelScope)
    private var songsRepository = SongListRepo(DataSource(appDatabase))

    var dataUpdates: DataUpdates? = null

    fun getSongs(isStored: Boolean) {
        viewModelScope.launch {
            if (isStored) {
                getSongsfromDB()
            } else getSongsfromServer()
        }
    }

    private suspend fun getSongsfromServer() {

        val response = songsRepository.getTopSongs()
        withContext(Dispatchers.Main) {
//            isProgressVisible.postValue(false)
            if (response.status == ApiResultStatus.Status.SUCCESS && response.data != null) {
                response.data.entryList?.map { it.mapDto() }?.let {
                    appDatabase.songDao().insertSongsList(
                       it
                    )
                }

                dataUpdates?.updateSongStored(true)

                getSongsfromDB()

            } else {
                dataUpdates?.updateSongStored(true)
                dataUpdates?.showError("Error", response.message.toString())
            }
        }

    }

    private suspend fun getSongsfromDB() {
        mutableSongList.postValue(songsRepository.getSongsfromDB())
    }
}