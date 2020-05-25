package net.azarquiel.example.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.example.api.MainRespository
import net.azarquiel.example.model.Artist
import net.azarquiel.example.model.RecentlyPlayedTrack
import net.azarquiel.example.model.Tracks

class RecentlyPlayedViewModel : ViewModel() {
    private var repository: MainRespository = MainRespository()

    fun getRecentlyPlayed(): LiveData<List<RecentlyPlayedTrack>> {
        val dataRecentlyPlayed = MutableLiveData<List<RecentlyPlayedTrack>>()
        GlobalScope.launch(Dispatchers.Main) {
            dataRecentlyPlayed.value = repository.getRecentlyPlayed()
        }
        return dataRecentlyPlayed
    }

}