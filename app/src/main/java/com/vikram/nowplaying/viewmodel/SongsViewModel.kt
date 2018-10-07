package com.vikram.nowplaying.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.vikram.nowplaying.db.Song
import com.vikram.nowplaying.repo.SongsRepo

class SongsViewModel(application: Application) : AndroidViewModel(application) {

    private var songsRepo: SongsRepo? = null
    private var songs: LiveData<List<Song>>? = null

    init {
        songsRepo = SongsRepo.getInstance(getApplication())
        songs = songsRepo?.getAllSongs()
    }

    fun getAllSongs(): LiveData<List<Song>>? {
        return songs
    }

    fun onFavoriteClicked(position: Int) {
        var song = songs?.value!![position]
        if (song.favorite == 1) song.favorite = 0 else song.favorite = 1
        songsRepo?.update(song)
    }

    fun getFavorites(): LiveData<List<Song>>? {
        return songsRepo?.getFavorites()
    }
}