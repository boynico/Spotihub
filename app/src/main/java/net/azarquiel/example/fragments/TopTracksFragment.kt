package net.azarquiel.example.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import net.azarquiel.example.R
import net.azarquiel.example.adapter.AdapterArtistTopTracks
import net.azarquiel.example.model.Tracks
import net.azarquiel.example.viewmodel.ArtistViewModel

/**
 * A simple [Fragment] subclass.
 */
class TopTracksFragment : Fragment() {

    private lateinit var viewModel: ArtistViewModel
    private lateinit var adapter: AdapterArtistTopTracks
    private lateinit var tracks: List<Tracks>
    private lateinit var ArtistID: String
    private lateinit var rvArtistPopularr: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_top_tracks, container, false)
        val bundle = this.arguments
        if (bundle != null) {
            ArtistID = bundle.getString("ArtistID")!!
            rvArtistPopularr = rootView.findViewById(R.id.rv_toptracks_artist) as RecyclerView
            getArtistTopTracks()
            initRVTopTracks()
        } else {
        }
        return rootView
    }

    private fun initRVTopTracks(){
        adapter = AdapterArtistTopTracks(this, R.layout.row_tracks_artist)
        rvArtistPopularr.adapter = adapter
        rvArtistPopularr.layoutManager = LinearLayoutManager(this.context)
    }


    private fun getArtistTopTracks(){
        viewModel = ViewModelProviders.of(this).get(ArtistViewModel::class.java)
        viewModel.getArtistTopTracks(ArtistID).observe(viewLifecycleOwner, Observer {it ->
            it?.let {
                tracks = it
                adapter.setArtistTopTracks(tracks)
            }
        })
    }

}
