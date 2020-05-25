package net.azarquiel.example.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import net.azarquiel.example.R
import net.azarquiel.example.adapter.AdapterArtistAlbum
import net.azarquiel.example.model.Album
import net.azarquiel.example.viewmodel.ArtistViewModel

/**
 * A simple [Fragment] subclass.
 */
class AlbumsFragment : Fragment() {

    private lateinit var viewModel: ArtistViewModel
    private lateinit var adapter: AdapterArtistAlbum
    private lateinit var albums: List<Album>
    private lateinit var ArtistID: String
    private lateinit var rvArtistAlbumm: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView2 = inflater.inflate(R.layout.fragment_albums, container, false)
        val bundle = this.arguments
        if (bundle != null) {
            ArtistID = bundle.getString("ArtistID")!!
            rvArtistAlbumm = rootView2.findViewById(R.id.rv_artist_albums) as RecyclerView
            getArtistAlbumss()
            initRVArtistAlbum()
        }
        return rootView2
    }

    private fun getArtistAlbumss(){
        viewModel = ViewModelProviders.of(this).get(ArtistViewModel::class.java)
        viewModel.getArtistAlbums(ArtistID).observe(viewLifecycleOwner, Observer {it ->
            it?.let {
                albums = it
                adapter.setArtistAlbums(albums)
            }
        })
    }


    private fun initRVArtistAlbum(){
        adapter = AdapterArtistAlbum(this, R.layout.grid_album_artist)
        rvArtistAlbumm.adapter = adapter
        rvArtistAlbumm.layoutManager = GridLayoutManager(this.context,2)

    }

}
