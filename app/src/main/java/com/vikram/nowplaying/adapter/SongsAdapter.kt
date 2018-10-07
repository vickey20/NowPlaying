package com.vikram.nowplaying.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vikram.nowplaying.R
import com.vikram.nowplaying.db.Song
import com.vikram.nowplaying.utilities.getLaymanTime
import kotlinx.android.synthetic.main.songs_list_item.view.*

enum class ViewType(val type: Int) {
    ROW(1),
    FAVORITE(2)
}

class SongsAdapter(val onClick: (Int, ViewType) -> Unit): RecyclerView.Adapter<SongsAdapter.ViewHolder>() {

    var songs = mutableListOf<Song>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(song: Song, position: Int) {
            itemView.tvSongName.text = song.title
            itemView.tvArtistName.text = song.artist
            itemView.tvTime.text = getLaymanTime(itemView.context, song.timestamp)

            if (song.favorite == 1) setFavoriteImage(R.drawable.star_filled_96) else setFavoriteImage(R.drawable.star_96)
            itemView.favorite.setOnClickListener { onClick(position, ViewType.FAVORITE) }
        }

        private fun setFavoriteImage(resId: Int) {
            itemView.favorite.setImageResource(resId)
        }
    }

    fun updateList(songs: List<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)
        notifyAdapterOfListUpdate()
    }

    private fun notifyAdapterOfListUpdate() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.songs_list_item))

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position], position)
    }
}

private fun ViewGroup.inflate(songs_list_item: Int): View {
    return LayoutInflater.from(this.context).inflate(songs_list_item, this, false)
}
