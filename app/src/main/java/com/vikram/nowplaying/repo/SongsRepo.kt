package com.vikram.nowplaying.repo

import android.arch.lifecycle.LiveData
import android.content.Context
import com.vikram.nowplaying.db.AppDatabase
import com.vikram.nowplaying.db.Song
import com.vikram.nowplaying.db.SongDao
import kotlinx.coroutines.experimental.*

class SongsRepo {

    private var songsDao: SongDao? = null
    private var songs: LiveData<List<Song>>? = null
    //private var executorService = Executors.newSingleThreadExecutor()

    init {
        var db = AppDatabase.getDatabase(context?.applicationContext!!)
        songsDao = db.songsDao()
        songs = songsDao?.getAllSongs()
    }

    companion object {
        @Volatile private var instance: SongsRepo? = null
        private var context: Context? = null

        fun getInstance(context: Context): SongsRepo {
            return instance?: synchronized(this) {
                this.context = context
                instance?: SongsRepo().also { instance = it }
            }
        }
    }

//    fun saveSong(song: Song) {
//        if (shouldSaveToDB(song)) {
//            executorService.execute { songsDao?.insert(song) }
//        }
//    }

    fun getAllSongs(): LiveData<List<Song>>? {
        return songsDao?.getAllSongs()
    }

    private fun shouldSaveToDB(song: Song): Boolean {
        return songsDao?.getLatestSong()?.songText != song.songText
    }

    fun saveSong(song: Song) {
        GlobalScope.launch{
//            val latestSong = async { songsDao?.getLatestSong() }.await()
//            if (latestSong?.songText != song.songText) {
//                async { songsDao?.insert(song) }
//            }

            val shouldSaveToDb = async { shouldSaveToDB(song) }.await()
            if (shouldSaveToDb) {
                async { songsDao?.insert(song) }
            }
        }
    }
}