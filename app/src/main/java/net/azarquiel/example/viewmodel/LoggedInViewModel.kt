package net.azarquiel.example.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.example.api.MainRespository
import net.azarquiel.example.model.Artist
import net.azarquiel.example.model.Tracks

class LoggedInViewModel : ViewModel() {
    private var repository: MainRespository = MainRespository()

    fun getTopArtists(): LiveData<List<Artist>> {
        val dataTopArtists = MutableLiveData<List<Artist>>()
        GlobalScope.launch(Dispatchers.Main) {
            dataTopArtists.value = repository.getTopArtists()
        }
        return dataTopArtists
    }

    fun getTopTracks(): LiveData<List<Tracks>> {
        val dataTopTracks = MutableLiveData<List<Tracks>>()
        GlobalScope.launch(Dispatchers.Main) {
            dataTopTracks.value = repository.getTopTracks()
        }
        return dataTopTracks
    }

}