package net.azarquiel.example.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.os.Build
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_comments.view.*
import net.azarquiel.example.model.ArtistComment
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

class AdapterFirebase(val context: Context, val layout: Int) :
    RecyclerView.Adapter<AdapterFirebase.ViewHolder>() {
    private var dataList: List<ArtistComment> = emptyList()

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

    internal fun setArtistComments(artistComments: List<ArtistComment>) {
        this.dataList = artistComments
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: ArtistComment) {
            itemView.tv_rowcomment_user.text = dataItem.user_id
            itemView.tv_rowcomment_comment.text = dataItem.comment


            //para obtener el valor de hace cuánto se comentó
            //primero convierto el tiempo de ahora y el de played_at a date:
            val current_time = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DateTimeFormatter.ISO_INSTANT.format(Instant.now())
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val commented_at = dataItem.timestamp
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val date1 = sdf.parse(current_time)
            val date2 = sdf.parse(commented_at)

            //calculo la diferencia en MS entre ambas fechas
            var diff: Int = (date1.getTime() - date2.getTime()).toInt()

            //la paso a Segundos
            var seconds_ago: Int = diff / 1000


            //calculo la diferencia en horas/mins a partir del valor en segundos
            var hours_ago = 0
            var mins_ago = 0
            var days_ago = 0
            if (seconds_ago < 3600) {
                mins_ago = seconds_ago / 60
                if (mins_ago == 1) {
                    itemView.tv_rowcomment_date.text = "hace ${mins_ago.toString()} min"
                }
                itemView.tv_rowcomment_date.text = "hace ${mins_ago.toString()} mins"
            } else if (seconds_ago >= 3600 && seconds_ago < 86400) {
                days_ago = seconds_ago / 3600
                if (days_ago == 1) {
                    itemView.tv_rowcomment_date.text = "hace ${days_ago.toString()} hora"
                } else {
                    itemView.tv_rowcomment_date.text = "hace ${days_ago.toString()} horas"
                }
            } else if (seconds_ago > 86400) {
                days_ago = seconds_ago / 86400
                if (days_ago == 1) {
                    itemView.tv_rowcomment_date.text = "hace ${days_ago.toString()} dia"
                } else {
                    itemView.tv_rowcomment_date.text = "hace ${days_ago.toString()} dias"
                }
            }

            Picasso.get().load("${dataItem.user_img}").into(itemView.iv_rowcomment)
            itemView.tag = dataItem
        }

    }


}