package com.vikram.nowplaying.repo

import android.arch.lifecycle.LiveData
import android.content.Context
import android.util.Log
import com.vikram.nowplaying.db.AppDatabase
import com.vikram.nowplaying.db.Song
import com.vikram.nowplaying.db.SongDao
import kotlinx.coroutines.experimental.*

class SongsRepo {

    companion object {
        @Volatile private var instance: SongsRepo? = null
        private var songsDao: SongDao? = null
        private lateinit var db: AppDatabase

        fun getInstance(context: Context): SongsRepo {
            return instance?: synchronized(this) {
                db = AppDatabase.getDatabase(context.applicationContext)
                songsDao = db.songsDao()
                instance?: SongsRepo().also { instance = it }
            }
        }
    }

    fun getAllSongs(): LiveData<List<Song>>? {
        return songsDao?.getAllSongs()
    }

    private fun shouldSaveToDB(song: Song): Boolean {
        return songsDao?.getLatestSong()?.songText != song.songText
    }

    fun saveSong(song: Song) {
        GlobalScope.launch{
            val shouldSaveToDb = async { shouldSaveToDB(song) }.await()
            if (shouldSaveToDb) {
                async { songsDao?.insert(song) }
            }
        }
    }

    fun update(song: Song) {
        GlobalScope.launch {
            async { songsDao?.update(song) }
        }
    }

    fun getFavorites(): LiveData<List<Song>>? {
        return songsDao?.getFavorites()
    }
}