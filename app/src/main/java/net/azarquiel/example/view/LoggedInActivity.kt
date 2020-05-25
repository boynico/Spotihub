package net.azarquiel.example.view

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.*
import android.view.Window
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_logged_in.*
import net.azarquiel.example.R
import net.azarquiel.example.adapter.AdapterLoggedInArtist
import net.azarquiel.example.adapter.AdapterLoggedInTrack
import net.azarquiel.example.model.Artist
import net.azarquiel.example.model.Tracks
import net.azarquiel.example.viewmodel.LoggedInViewModel
import org.jetbrains.anko.*

class LoggedInActivity : AppCompatActivity() {

    val FLAGS = (View.SYSTEM_UI_FLAG_LOW_PROFILE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

    private lateinit var viewModel: LoggedInViewModel

    //Artist
    private lateinit var adapterArtist: AdapterLoggedInArtist
    private lateinit var artists: List<Artist>

    //Track
    private lateinit var adapterTrack: AdapterLoggedInTrack
    private lateinit var tracks: List<Tracks>

    //Track selected
    private lateinit var trackselected: Tracks

    //Reproductor
    private var mp2: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)
        getTopArtists()
        initRVTopArtists()
        getTopTracks()
        initRVTopTracks()
        hideNavigation()
        btn_history_loggedin.setOnClickListener{
            val intent = Intent(this, RecentlyPlayedActivity::class.java)
            startActivity(intent)
        }
}

    //Artist
    private fun getTopArtists() {
        viewModel = ViewModelProviders.of(this).get(LoggedInViewModel::class.java)
        viewModel.getTopArtists().observe(this, Observer { it ->
            it?.let {
                artists = it
                adapterArtist.setArtists(artists)
            }
        })
    }

    private fun initRVTopArtists() {
        adapterArtist = AdapterLoggedInArtist(this, R.layout.grid_artists_loggedin)
        rv_artistGrid_loggedInActivity.adapter = adapterArtist
        rv_artistGrid_loggedInActivity.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    //Tracks
    private fun getTopTracks() {
        viewModel = ViewModelProviders.of(this).get(LoggedInViewModel::class.java)
        viewModel.getTopTracks().observe(this, Observer {it ->
            it?.let {
                tracks = it
                adapterTrack.setTracks(tracks)
            }
        })
    }

    private fun initRVTopTracks() {
        adapterTrack = AdapterLoggedInTrack(this, R.layout.row_tracks_loggedin)
        rv_trackrow_loggedinactivity.adapter = adapterTrack
        rv_trackrow_loggedinactivity.layoutManager = LinearLayoutManager(this)
    }

    //fullScreen immersive
    fun hideNavigation() {
        window.decorView.apply {
            systemUiVisibility = FLAGS
        }
    }

    fun trackPressed(v: View){
        trackselected = v.tag as Tracks
        stopplaying()
        //Esto es un simple dialogo de anko para registrarse
        alert {
            title = "Seleccione"
            customView {
                verticalLayout {
                    lparams(width = wrapContent, height = wrapContent)
                    val btnPreview = button {
                        text = "Abrir en Spotify"
                        padding = dip(16)
                        var trackselected_id = trackselected.id
                        setOnClickListener {
                            redirectSpotify(trackselected_id)
                            hideNavigation()
                            stopplaying()
                        }
                    }
                    val btnRedirect = button {
                        text = "Preview"
                        padding = dip(16)
                        var trackselected_url = trackselected.preview_url
                        setOnClickListener {
                            playPreview(trackselected_url)
                            hideNavigation()
                        }
                    }
                }
                negativeButton("Cancelar") {
                    stopplaying()
                    hideNavigation()
                } // Para que salga un cancelar en el dialogo
            }
        }.show()
    }

    //media player
    fun stopplaying(){
        if (mp2!= null){
            mp2!!.stop()
            mp2!!.release()
            mp2 = null
        }
    }

    fun playPreview(trackselected_url: String){
        val url = trackselected_url // your URL here
        mp2 = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(url)
            prepare()
            start()
        }
    }

    //recirect a spotify
    fun redirectSpotify(trackselected_id: String){
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://open.spotify.com/track/${trackselected_id}")
        startActivity(openURL)
    }

    //abro search activity
    fun onClickSearchActivity(v: View) {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

    //abro artist activity
    fun onClickArtist(v: View) {
        val artistselected = v.tag as Artist
        val intent = Intent(this, ArtistActivity::class.java)
        intent.putExtra("artist", artistselected)
        startActivity(intent)
    }
}
