package net.azarquiel.example.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.example.api.MainRespository
import net.azarquiel.example.model.Artist
import net.azarquiel.example.model.MyUser
import net.azarquiel.example.model.RecentlyPlayedTrack
import net.azarquiel.example.model.Tracks

class ArtistCommentsViewModel : ViewModel() {
    private var repository: MainRespository = MainRespository()

    fun getUserData(): LiveData<MyUser> {
        val dataMyUser = MutableLiveData<MyUser>()
        GlobalScope.launch(Dispatchers.Main) {
            dataMyUser.value = repository.getUserData()
        }
        return dataMyUser
    }

}