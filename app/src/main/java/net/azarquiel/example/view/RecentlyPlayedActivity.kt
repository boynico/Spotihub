package net.azarquiel.example.view

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_logged_in.*
import kotlinx.android.synthetic.main.activity_recently_played.*
import kotlinx.android.synthetic.main.row_tracks_recentlyplayed.*
import net.azarquiel.example.R
import net.azarquiel.example.adapter.AdapterRecentlyPlayed
import net.azarquiel.example.model.RecentlyPlayedTrack
import net.azarquiel.example.model.Tracks
import net.azarquiel.example.viewmodel.RecentlyPlayedViewModel
import org.jetbrains.anko.*

class RecentlyPlayedActivity : AppCompatActivity() {

    val FLAGS = (View.SYSTEM_UI_FLAG_LOW_PROFILE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    private lateinit var viewModel: RecentlyPlayedViewModel
    private lateinit var adapter: AdapterRecentlyPlayed
    private lateinit var tracks: List<RecentlyPlayedTrack>
    private lateinit var trackselected: RecentlyPlayedTrack
    private var mp: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recently_played)
        hideNavigation()
        getRecentlyPlayed()
        initRVRecentlyPlayed()
    }

    fun hideNavigation() {
        window.decorView.apply {
            systemUiVisibility = FLAGS
        }
    }

    private fun getRecentlyPlayed() {
        viewModel = ViewModelProviders.of(this).get(RecentlyPlayedViewModel::class.java)
        viewModel.getRecentlyPlayed().observe(this, Observer {it ->
            it?.let{
                tracks = it
                adapter.setRecentlyPlayed(tracks)
            }
        })
        initRVRecentlyPlayed()
    }

    private fun initRVRecentlyPlayed() {
        adapter = AdapterRecentlyPlayed(this, R.layout.row_tracks_recentlyplayed)
        rv_tracks_recentlyplayed.adapter = adapter
        rv_tracks_recentlyplayed.layoutManager = LinearLayoutManager(this)
    }

    fun trackPressed(v: View){
        hideNavigation()
        stopplaying()
        trackselected = v.tag as RecentlyPlayedTrack
        //Esto es un simple dialogo de anko para registrarse
        alert {
            title = "Seleccione"
            customView {
                verticalLayout {
                    lparams(width = wrapContent, height = wrapContent)
                    val btnPreview = button {
                        text = "Abrir en Spotify"
                        padding = dip(16)
                        var trackselected_id = trackselected.track.id
                        setOnClickListener {
                            redirectSpotify(trackselected_id)
                            stopplaying()
                            hideNavigation()
                        }
                    }
                    val btnRedirect = button {
                        text = "Preview"
                        padding = dip(16)
                        var trackselected_url = trackselected.track.preview_url
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

    fun stopplaying(){
        if (mp!= null){
            mp!!.stop()
            mp!!.release()
            mp = null
        }
    }

    fun playPreview(trackselected_url: String){
        val url = trackselected_url // your URL here
        mp = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(url)
            prepare()
            start()
        }
    }

    fun redirectSpotify(trackselected_id: String){
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://open.spotify.com/track/${trackselected_id}")
        startActivity(openURL)
    }
}
