package ru.netology.singlealbum.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.netology.singlealbum.model.AlbumModel
import ru.netology.singlealbum.repository.AlbumRepository
import ru.netology.singlealbum.repository.AlbumRepositoryImpl

class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AlbumRepository = AlbumRepositoryImpl()

    private val _album = MutableLiveData(AlbumModel())
    val album: LiveData<AlbumModel>
        get() = _album

    init {
        loadAlbum()
    }

    private fun loadAlbum() = viewModelScope.launch {
        try {
            _album.value = AlbumModel(loading = true)
            _album.value = AlbumModel(repository.getAlbum())
//            _album.value = AlbumModel()

        } catch (e: Exception) {
            _album.value = AlbumModel(error = true)
        }
    }

}