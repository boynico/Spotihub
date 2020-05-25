package net.azarquiel.example.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.example.api.MainRespository
import net.azarquiel.example.model.*

class ArtistViewModel : ViewModel() {
    private var repository: MainRespository = MainRespository()

    //get singles de un artist - Artist Activity
    fun getArtistSingles(id: String): LiveData<List<Album>> {
        val dataArtistSingle = MutableLiveData<List<Album>>()
        GlobalScope.launch(Dispatchers.Main) {
            dataArtistSingle.value = repository.getArtistSingles(id)
        }
        return dataArtistSingle
    }

    //get artistas relacionados de un artist - Artist Activity
    fun getArtistRelatedArtists(id: String): LiveData<List<Artist>> {
        val dataArtistRelatedArtists = MutableLiveData<List<Artist>>()
        GlobalScope.launch(Dispatchers.Main) {
            dataArtistRelatedArtists.value = repository.getArtistRelatedArtists(id)
        }
        return dataArtistRelatedArtists
    }

    //get top tracks de un artist  - Artist Activity
    fun getArtistTopTracks(id: String): LiveData<List<Tracks>> {
        val dataArtistTopTracks = MutableLiveData<List<Tracks>>()
        GlobalScope.launch(Dispatchers.Main) {
            dataArtistTopTracks.value = repository.getArtistTopTracks(id)
        }
        return dataArtistTopTracks
    }

    //get albums de un artist - Artist Activity
    fun getArtistAlbums(id: String): LiveData<List<Album>> {
        val dataArtistAlbum = MutableLiveData<List<Album>>()
        GlobalScope.launch(Dispatchers.Main) {
            dataArtistAlbum.value = repository.getArtistAlbums(id)
        }
        return dataArtistAlbum
    }

}