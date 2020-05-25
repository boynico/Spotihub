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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search.*
import net.azarquiel.example.R
import net.azarquiel.example.adapter.AdapterSearchAlbum
import net.azarquiel.example.adapter.AdapterSearchArtist
import net.azarquiel.example.adapter.AdapterSearchTrack
import net.azarquiel.example.model.Album
import net.azarquiel.example.model.Artist
import net.azarquiel.example.model.RecentlyPlayedTrack
import net.azarquiel.example.model.Tracks
import net.azarquiel.example.viewmodel.SearchViewModel
import org.jetbrains.anko.*
import java.net.URLEncoder

class SearchActivity : AppCompatActivity() {

    private lateinit var adapteralbum: AdapterSearchAlbum
    private lateinit var adaptertracks: AdapterSearchTrack
    private lateinit var adapterartist: AdapterSearchArtist
    private lateinit var viewModel: SearchViewModel
    private lateinit var albums: List<Album>
    private lateinit var tracks: List<Tracks>
    private lateinit var artists: List<Artist>
    private lateinit var trackselected: Tracks
    private lateinit var artistselected: Artist
    private lateinit var albumselected: Album
    private var mp: MediaPlayer? = null

    val FLAGS = (View.SYSTEM_UI_FLAG_LOW_PROFILE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        //listener boton search Album
        btn_album_search.setOnClickListener {
            searchAlbum()
            initRVSearchAlbum()
        }

        //listener boton sarch artist
        btn_artist_search.setOnClickListener {
            searchArtist()
            initRVSearchArtist()
        }

        //listener boton search track
        btn_track_search.setOnClickListener {
            searchTrack()
            initRVSearchTrack()
        }
    }

    //Ejecuto search album
    private fun initRVSearchAlbum() {
        adapteralbum = AdapterSearchAlbum(this, R.layout.grid_album_search)
        rv_search.adapter = adapteralbum
        rv_search.layoutManager = GridLayoutManager(this,2)
    }

    private fun searchAlbum() {
        val album_search_string: String
        album_search_string = (URLEncoder.encode(tf_search.text.toString(), "utf-8"))
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        viewModel.searchAlbum(album_search_string).observe(this, Observer {it ->
            it?.let {
                albums = it.items
                adapteralbum.setSearchAlbums(albums)
            }
        })
    }

    //Ejecuto search artist
    private fun initRVSearchArtist() {
        adapterartist = AdapterSearchArtist(this, R.layout.grid_artists_search)
        rv_search.adapter = adapterartist
        rv_search.layoutManager = GridLayoutManager(this,2)
    }

    private fun searchArtist() {
        val artist_search_string: String
        artist_search_string = (URLEncoder.encode(tf_search.text.toString(), "utf-8"))
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        viewModel.searchArtist(artist_search_string).observe(this, Observer {it ->
            it?.let {
                artists = it.items
                adapterartist.setSearchArtist(artists)
            }
        })
    }

    //Ejecuto search track

    private fun initRVSearchTrack() {
        adaptertracks = AdapterSearchTrack(this, R.layout.row_tracks_search)
        rv_search.adapter = adaptertracks
        rv_search.layoutManager = LinearLayoutManager(this)
    }

    private fun searchTrack() {
        val track_search_string: String
        track_search_string = (URLEncoder.encode(tf_search.text.toString(), "utf-8"))
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        viewModel.searchTrack(track_search_string).observe(this, Observer {it ->
            it?.let {
                tracks = it.items
                adaptertracks.setSearchTrack(tracks)
            }
        })
    }

    //metodos onclick

    //cancion
    fun trackSearchPressed (v: View){
        hideNavigation()
        stopplaying()
        trackselected = v.tag as Tracks
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
                            stopplaying()
                            hideNavigation()
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

    fun hideNavigation() {
        window.decorView.apply {
            systemUiVisibility = FLAGS
        }
    }


    //album
    fun onClickAlbumSearch(v: View){
        albumselected = v.tag as Album
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://open.spotify.com/album/${albumselected.id}")
        startActivity(openURL)
    }

    //artist
    fun onClickArtistSearch(v: View){
        artistselected = v.tag as Artist
        val intent = Intent(this, ArtistActivity::class.java)
        intent.putExtra("artist", artistselected)
        startActivity(intent)
    }
}
