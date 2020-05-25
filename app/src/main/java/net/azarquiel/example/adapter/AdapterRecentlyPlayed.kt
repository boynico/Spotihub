package net.azarquiel.example.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_tracks_loggedin.view.*
import kotlinx.android.synthetic.main.row_tracks_recentlyplayed.view.*
import net.azarquiel.example.R
import net.azarquiel.example.model.RecentlyPlayedTrack
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class AdapterRecentlyPlayed(val context: Context, val layout: Int) :
    RecyclerView.Adapter<AdapterRecentlyPlayed.ViewHolder>() {
    private var dataList: List<RecentlyPlayedTrack> = emptyList()

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

    internal fun setRecentlyPlayed(tracks: List<RecentlyPlayedTrack>) {
        this.dataList = tracks
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: RecentlyPlayedTrack) {
            itemView.tv_rowtracks_title_recentlyplayed.text = dataItem.track.name
            Picasso.get().load("${dataItem.track.album.images[0].url}").into(itemView.iv_rowtracks_recentlyplayed)

            if(dataItem.track.album.images.size>0){
                itemView.tv_rowtracks_info_recentlyplayed.text = "${dataItem.track.artists[0].name} · ${dataItem.track.album.name}"
                }
            itemView.tag = dataItem

            //menú desplegable
            itemView.tv_rowtracks_btn_recentlyplayed.setOnClickListener {
                val popupMenu: PopupMenu = PopupMenu(this.context, itemView.tv_rowtracks_btn_recentlyplayed)
                popupMenu.menuInflater.inflate(R.menu.menu_top_tracks, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when(item.itemId) {
                        R.id.mnu_album ->
                            gotoAlbum(dataItem.track.album.id)
                        R.id.mnu_artista ->
                            gotoArtist(dataItem.track.artists[0].id)
                    }
                    true
                })
                popupMenu.show()
            }




            //para obtener el valor de hace cuánto lo escucho
            //primero convierto el tiempo de ahora y el de played_at a date:
            val current_time = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DateTimeFormatter.ISO_INSTANT.format(Instant.now())
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val played_at_time = dataItem.played_at
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val date1 = sdf.parse(current_time)
            val date2 = sdf.parse(played_at_time)

            //calculo la diferencia en MS entre ambas fechas
            var diff: Int = (date1.getTime() - date2.getTime()).toInt()

            //la paso a Segundos
            var seconds_ago: Int = diff/1000


            //calculo la diferencia en horas/mins a partir del valor en segundos
            var hours_ago = 0
            var mins_ago = 0
            var days_ago = 0
            if (seconds_ago < 3600){
                mins_ago = seconds_ago/60
                if (mins_ago == 1){
                    itemView.tv_rowtracks_time_recentlyplayed.text = "hace ${mins_ago.toString()} min"
                }
                itemView.tv_rowtracks_time_recentlyplayed.text = "hace ${mins_ago.toString()} mins"
            } else if(seconds_ago >= 3600 && seconds_ago < 86400){
                hours_ago = seconds_ago/3600
                if(hours_ago == 1){
                    itemView.tv_rowtracks_time_recentlyplayed.text = "hace ${hours_ago.toString()} hora"
                }
                else {
                    itemView.tv_rowtracks_time_recentlyplayed.text = "hace ${hours_ago.toString()} horas"
                }

            } else if (seconds_ago > 86400) {
                days_ago = seconds_ago / 86400
                if (days_ago == 1) {
                    itemView.tv_rowtracks_time_recentlyplayed.text = "hace ${days_ago.toString()} dia"
                } else {
                    itemView.tv_rowtracks_time_recentlyplayed.text = "hace ${days_ago.toString()} dias"
                }
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