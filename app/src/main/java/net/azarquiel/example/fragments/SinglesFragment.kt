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
import net.azarquiel.example.adapter.AdapterArtistSingle
import net.azarquiel.example.model.Album
import net.azarquiel.example.viewmodel.ArtistViewModel

/**
 * A simple [Fragment] subclass.
 */
class SinglesFragment : Fragment() {

    private lateinit var viewModel: ArtistViewModel
    private lateinit var adaptersingles: AdapterArtistSingle
    private lateinit var albums: List<Album>
    private lateinit var ArtistID: String
    private lateinit var rvArtistSinglee: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView4 = inflater.inflate(R.layout.fragment_singles, container, false)
        val bundle = this.arguments
        if (bundle != null) {
            Log.d("TAG", "bundle no null")
            ArtistID = bundle.getString("ArtistID")!!
            rvArtistSinglee = rootView4.findViewById(R.id.rv_artist_singles) as RecyclerView
            Log.d("TAG", "antes de metodos")
            getArtistSingless()
            initRVArtistSingle()
            Log.d("TAG", "despues de metodos")
        } else {
            Log.d("TAG", "bundle null")
        }
        return rootView4
    }

    private fun getArtistSingless() {
        Log.d("TAG", "hasta aquí si llego")
        viewModel = ViewModelProviders.of(this).get(ArtistViewModel::class.java)
        viewModel.getArtistSingles(ArtistID).observe(viewLifecycleOwner, Observer { it ->
            it?.let {
                albums = it
                Log.d("TAG", albums.toString())
                adaptersingles.setArtistSingles(albums)
            }
        })
    }

    private fun initRVArtistSingle() {
        Log.d("TAG", "metodo de initRV")
        adaptersingles = AdapterArtistSingle(this, R.layout.grid_singles_artist)
        rvArtistSinglee.adapter = adaptersingles
        rvArtistSinglee.layoutManager = GridLayoutManager(this.context, 2)

    }

}
