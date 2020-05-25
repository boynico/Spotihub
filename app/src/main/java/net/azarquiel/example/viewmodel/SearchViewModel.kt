package net.azarquiel.example.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.example.api.MainRespository
import net.azarquiel.example.model.*

class SearchViewModel : ViewModel() {
    private var repository: MainRespository = MainRespository()

    fun searchAlbum(stringAlbum: String): LiveData<SearchAlbumClass> {
        val dataSearchAlbum = MutableLiveData<SearchAlbumClass>()
        GlobalScope.launch(Dispatchers.Main) {
            dataSearchAlbum.value = repository.searchAlbum(stringAlbum)
        }
        return dataSearchAlbum
    }

    fun searchArtist(stringArtist: String): LiveData<SearchArtistClass> {
        val dataSearchArtist = MutableLiveData<SearchArtistClass>()
        GlobalScope.launch(Dispatchers.Main) {
            dataSearchArtist.value = repository.searchArtist(stringArtist)
        }
        return dataSearchArtist
    }

    fun searchTrack(stringTrack: String): LiveData<SeachTrackClass> {
        val dataSearchTrack = MutableLiveData<SeachTrackClass>()
        GlobalScope.launch(Dispatchers.Main) {
            dataSearchTrack.value = repository.searchTrack(stringTrack)
        }
        return dataSearchTrack
    }

}