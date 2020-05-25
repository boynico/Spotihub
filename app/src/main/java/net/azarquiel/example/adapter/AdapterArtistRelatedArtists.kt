package net.azarquiel.example.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_artists_artist.view.*
import net.azarquiel.example.fragments.RelatedArtistsFragment
import net.azarquiel.example.model.Artist

class AdapterArtistRelatedArtists(val context: RelatedArtistsFragment, val layout: Int) :
    RecyclerView.Adapter<AdapterArtistRelatedArtists.ViewHolder>() {
    private var dataList: List<Artist> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setRA(artists: List<Artist>) {
        this.dataList = artists
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: RelatedArtistsFragment) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Artist) {
            itemView.tv_RA.text = dataItem.name
            if(dataItem.images.size>0){
                Picasso.get().load("${dataItem.images[0].url}").into(itemView.iv_RA)
            }

            itemView.tag = dataItem
        }
    }
}