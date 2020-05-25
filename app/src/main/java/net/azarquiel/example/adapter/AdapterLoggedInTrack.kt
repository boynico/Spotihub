package net.azarquiel.example.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_artists_loggedin.view.*
import kotlinx.android.synthetic.main.row_tracks_loggedin.view.*
import net.azarquiel.example.R
import net.azarquiel.example.model.Artist
import net.azarquiel.example.model.Tracks

class AdapterLoggedInTrack(val context: Context, val layout: Int) :
    RecyclerView.Adapter<AdapterLoggedInTrack.ViewHolder>() {
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

    internal fun setTracks(tracks: List<Tracks>) {
        this.dataList = tracks
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Tracks) {
            itemView.tv_rowtracks_title.text = dataItem.name
            itemView.tv_rowtracks_info.text = "${dataItem.artists[0].name} · ${dataItem.album.name}"
            var lengthTemporal = dataItem.duration_ms
            var minstemporal = lengthTemporal / 1000 / 60
            var secstemporal = lengthTemporal / 1000 % 60
            if (secstemporal < 10) {
                itemView.tv_rowtracks_length.text = "${minstemporal} : 0${secstemporal}"
            } else {
                itemView.tv_rowtracks_length.text = "${minstemporal} : ${secstemporal}"
            }
            if(dataItem.album.images.size>0){
                Picasso.get().load("${dataItem.album.images[0].url}").into(itemView.iv_rowtracks_album)
            }
            itemView.tag = dataItem

            //menu de escuchar preview o abrir en spotify
            /*itemView.tv_rowtracks_title.setOnClickListener {
                val dialog = MaterialDialog(this.context).customView(R.layout.dialog_tracks_loggedin)
                dialog.show()
            }*/


            //menu desplegable de los 3 puntos
            itemView.tv_rowtracks_btn.setOnClickListener {
                val popupMenu: PopupMenu = PopupMenu(this.context, itemView.tv_rowtracks_btn)
                popupMenu.menuInflater.inflate(R.menu.menu_top_tracks, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when (item.itemId) {
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