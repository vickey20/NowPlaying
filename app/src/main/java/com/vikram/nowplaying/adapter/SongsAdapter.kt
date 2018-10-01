package com.vikram.nowplaying.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vikram.nowplaying.R
import com.vikram.nowplaying.db.Song
import com.vikram.nowplaying.utilities.getLaymanTime
import kotlinx.android.synthetic.main.songs_list_item.view.*

class SongsAdapter: RecyclerView.Adapter<SongsAdapter.ViewHolder>() {

    var songs = mutableListOf<Song>()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(song: Song) {
            itemView.tvSongName.text = song.title
            itemView.tvArtistName.text = song.artist
            itemView.tvTime.text = getLaymanTime(itemView.context, song.timestamp)
        }
    }

    fun updateList(songs: List<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.songs_list_item))

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position])
    }
}

private fun ViewGroup.inflate(songs_list_item: Int): View {
    return LayoutInflater.from(this.context).inflate(songs_list_item, this, false)
}
