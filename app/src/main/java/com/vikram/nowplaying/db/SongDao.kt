package com.vikram.nowplaying.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface SongDao {

    @Insert
    fun insert(song: Song)

    @Insert
    fun update(song: Song)

    @Insert
    fun delete(song: Song)

    @Query("SELECT * FROM songs ORDER BY id DESC")
    fun getAllSongs(): LiveData<List<Song>>

    @Query("SELECT * FROM songs WHERE timestamp = (SELECT MAX(timestamp) FROM songs)")
    fun getLatestSong(): Song

}