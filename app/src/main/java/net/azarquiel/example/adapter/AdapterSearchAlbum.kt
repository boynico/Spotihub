package net.azarquiel.example.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_album_search.view.*
import net.azarquiel.example.model.Album

class AdapterSearchAlbum(val context: Context, val layout: Int) :
    RecyclerView.Adapter<AdapterSearchAlbum.ViewHolder>() {
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

    internal fun setSearchAlbums(albums: List<Album>) {
        this.dataList = albums
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Album) {
            itemView.tv_gridalbum_title.text = dataItem.name
            val temporalreleasedate = dataItem.release_date.toString().dropLast(6)

            itemView.tv_gridalbum_year.text = temporalreleasedate
            if(dataItem.images.size>0){
                Picasso.get().load("${dataItem.images[0].url}").into(itemView.iv_gridalbum_search)
            }

            itemView.tag = dataItem
        }
    }
}