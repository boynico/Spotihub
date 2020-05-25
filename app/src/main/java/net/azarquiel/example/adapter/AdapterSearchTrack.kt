package net.azarquiel.example.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_tracks_loggedin.view.*
import kotlinx.android.synthetic.main.row_tracks_search.view.*
import net.azarquiel.example.R
import net.azarquiel.example.model.Tracks

class AdapterSearchTrack(val context: Context, val layout: Int) :
    RecyclerView.Adapter<AdapterSearchTrack.ViewHolder>() {
    private var dataList: List<Tracks> = emptyList()

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

    internal fun setSearchTrack(tracks: List<Tracks>) {
        this.dataList = tracks
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Tracks) {
            itemView.tv_rowtracks_title_search.text = dataItem.name
            if(dataItem.album.images.size>0){
                Picasso.get().load("${dataItem.album.images[0].url}").into(itemView.iv_rowtracks_album_search)
            }

            itemView.tv_rowtracks_info_search.text = "${dataItem.artists[0].name} · ${dataItem.album.name}"
            itemView.tag = dataItem

            //menú desplegable
            itemView.tv_rowtracks_btn_search.setOnClickListener {
                val popupMenu: PopupMenu = PopupMenu(this.context, itemView.tv_rowtracks_btn_search)
                popupMenu.menuInflater.inflate(R.menu.menu_top_tracks, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when(item.itemId) {
                        R.id.mnu_album ->
                            gotoAlbum(dataItem.album.id)
                        R.id.mnu_artista ->
                            gotoArtist(dataItem.artists[0].id)
                    }
                    true
                })
                popupMenu.show()
            }
        }

        private fun gotoAlbum(album_id: String) {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://open.spotify.com/album/${album_id}")
            this.context.startActivity(openURL)
        }

        private fun gotoArtist(artista_id: String) {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://open.spotify.com/artist/${artista_id}")
            this.context.startActivity(openURL)
        }
    }
}