package net.azarquiel.example.view

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_artist.*
import net.azarquiel.example.R
import net.azarquiel.example.adapter.AdapterViewPager
import net.azarquiel.example.fragments.AlbumsFragment
import net.azarquiel.example.fragments.RelatedArtistsFragment
import net.azarquiel.example.fragments.SinglesFragment
import net.azarquiel.example.fragments.TopTracksFragment
import net.azarquiel.example.model.Album
import net.azarquiel.example.model.Artist
import net.azarquiel.example.model.Tracks
import org.jetbrains.anko.*

class ArtistActivity : AppCompatActivity() {
    private lateinit var artist: Artist
    private lateinit var trackselected: Tracks
    private lateinit var artistselected: Artist
    private lateinit var albumselected: Album
    val FLAGS =
        (View.SYSTEM_UI_FLAG_LOW_PROFILE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

    //Reproductor
    private var mp2: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist)
        artist = intent.getSerializableExtra("artist") as Artist
        setupViewPager(vp_artist)
        fillArtistData()
        hideNavigation()
        btn_artist.setOnClickListener {
            val intent = Intent(this, ArtistCommentsActivity::class.java)
            intent.putExtra("artist", artist)
            startActivity(intent)
        }
    }


    //creo el viewpager
    private fun setupViewPager(viewPager: ViewPager) {
        val adapterVP = AdapterViewPager(this, supportFragmentManager)
        val bundle = Bundle()
        bundle.putString("ArtistID", artist.id.toString())
        //fragment singles
        val fragmentosingles = SinglesFragment()
        fragmentosingles.setArguments(bundle)
        adapterVP.addFragment(fragmentosingles, "SINGLES")

        //fragmentalbums
        val fragmentalbums = AlbumsFragment()
        fragmentalbums.setArguments(bundle)
        adapterVP.addFragment(fragmentalbums, "ALBUMS")

        //fragment top tracks
        val fragmentotoptracks = TopTracksFragment()
        fragmentotoptracks.setArguments(bundle)
        adapterVP.addFragment(fragmentotoptracks, "TOP TRACKS")
        //fragment related artists
        val fragmentorelatedartist = RelatedArtistsFragment()
        fragmentorelatedartist.setArguments(bundle)
        adapterVP.addFragment(fragmentorelatedartist, "SINGLES")
        viewPager.adapter = adapterVP
        viewPager.setCurrentItem(2)
    }


    //lleno los datos de la pantalla (foto - nombre)
    private fun fillArtistData() {
        tv_artist.text = artist.name
        Picasso.get().load("${artist.images[0].url}").into(iv_artist)
    }

    fun hideNavigation() {
        window.decorView.apply {
            systemUiVisibility = FLAGS
        }
    }

    //metodos on click

    //single
    fun onClickSingleArtist(v: View) {
        albumselected = v.tag as Album
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://open.spotify.com/album/${albumselected.id}")
        startActivity(openURL)
    }

    //album
    fun onClickAlbumArtist(v: View) {
        albumselected = v.tag as Album
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://open.spotify.com/album/${albumselected.id}")
        startActivity(openURL)
    }

    //track
    fun onClickTracksArtist(v: View) {
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
    fun stopplaying() {
        if (mp2 != null) {
            mp2!!.stop()
            mp2!!.release()
            mp2 = null
        }
    }

    fun playPreview(trackselected_url: String) {
        val url = trackselected_url // your URL here
        mp2 = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(url)
            prepare()
            start()
        }
    }

    //recirect a spotify
    fun redirectSpotify(trackselected_id: String) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://open.spotify.com/track/${trackselected_id}")
        startActivity(openURL)
    }

    //artist
    fun onClickArtistArtist(v: View) {
        artistselected = v.tag as Artist
        val intent = Intent(this, ArtistActivity::class.java)
        intent.putExtra("artist", artistselected)
        startActivity(intent)
    }
}
