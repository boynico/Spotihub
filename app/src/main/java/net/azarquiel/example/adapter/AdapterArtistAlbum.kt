package net.azarquiel.example.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_album_artist.view.*
import net.azarquiel.example.fragments.AlbumsFragment
import net.azarquiel.example.model.Album

class AdapterArtistAlbum(val context: AlbumsFragment, val layout: Int) :
    RecyclerView.Adapter<AdapterArtistAlbum.ViewHolder>() {
    private var dataList: List<Album> = emptyList()

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

    internal fun setArtistAlbums(albums: List<Album>) {
        this.dataList = albums
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: AlbumsFragment) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Album) {
            itemView.tv_artistactivity_albumname.text = dataItem.name
            itemView.tv_artistactivity_albumyear.text = dataItem.release_date.dropLast(6)
            if(dataItem.images.size>0){
                Picasso.get().load("${dataItem.images[0].url}").into(itemView.iv_artistactivity_album)
            }
            itemView.tag = dataItem
        }
    }
}