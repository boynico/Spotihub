package net.azarquiel.example.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_artists_search.view.*
import net.azarquiel.example.model.Artist

class AdapterSearchArtist(val context: Context, val layout: Int) :
    RecyclerView.Adapter<AdapterSearchArtist.ViewHolder>() {
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

    internal fun setSearchArtist(artists: List<Artist>) {
        this.dataList = artists
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Artist) {
            itemView.tv_gridartists_search.text = dataItem.name
            if(dataItem.images.size>0){
                Picasso.get().load("${dataItem.images[0].url}").into(itemView.iv_gridartists_search)
            }
            itemView.tag = dataItem
        }
    }
}