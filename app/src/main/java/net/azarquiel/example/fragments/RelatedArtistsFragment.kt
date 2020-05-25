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
import net.azarquiel.example.adapter.AdapterArtistRelatedArtists
import net.azarquiel.example.model.Artist
import net.azarquiel.example.viewmodel.ArtistViewModel

/**
 * A simple [Fragment] subclass.
 */
class RelatedArtistsFragment : Fragment() {

    private lateinit var viewModel: ArtistViewModel
    private lateinit var adapter: AdapterArtistRelatedArtists
    private lateinit var artists: List<Artist>
    private lateinit var artist_id: String
    private lateinit var rvArtistRelatedArtistss: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView3 = inflater.inflate(R.layout.fragment_related_artists, container, false)
        val bundle = this.arguments
        if (bundle != null){
            artist_id = bundle.getString("ArtistID")!!
            rvArtistRelatedArtistss = rootView3.findViewById(R.id.rv_RA)as RecyclerView
            getRA()
            initRVRA()
        } else {
        }
        return rootView3
    }
    private fun getRA(){
        viewModel = ViewModelProviders.of(this).get(ArtistViewModel::class.java)
        viewModel.getArtistRelatedArtists(artist_id).observe(viewLifecycleOwner, Observer {it ->
            it?.let {
                artists = it
                adapter.setRA(artists)
            }
        })
    }

    private fun initRVRA(){
        adapter = AdapterArtistRelatedArtists(this, R.layout.grid_artists_artist)
        rvArtistRelatedArtistss.adapter = adapter
        rvArtistRelatedArtistss.layoutManager = GridLayoutManager(this.context, 2)
    }

}
