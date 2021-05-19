package ru.netology.singlealbum.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.netology.singlealbum.db.AppDb
import ru.netology.singlealbum.model.AlbumModel
import ru.netology.singlealbum.model.TrackModel
import ru.netology.singlealbum.repository.AlbumRepository
import ru.netology.singlealbum.repository.AlbumRepositoryImpl

class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AlbumRepository =
        AlbumRepositoryImpl(AppDb.getInstance(context = application).trackDao())

    val data: LiveData<TrackModel> = repository.data.map(::TrackModel)

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

        } catch (e: Exception) {
            _album.value = AlbumModel(error = true)
        }
    }


}