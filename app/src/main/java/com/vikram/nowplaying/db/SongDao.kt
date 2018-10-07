package com.vikram.nowplaying.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.vikram.nowplaying.utilities.FAVORITE
import com.vikram.nowplaying.utilities.ID
import com.vikram.nowplaying.utilities.TABLE_SONGS
import com.vikram.nowplaying.utilities.TIMESTAMP

@Dao
interface SongDao {

    @Insert
    fun insert(song: Song)

    @Update
    fun update(song: Song)

    @Delete
    fun delete(song: Song)

    @Query("SELECT * FROM $TABLE_SONGS ORDER BY $ID DESC")
    fun getAllSongs(): LiveData<List<Song>>

    @Query("SELECT * FROM $TABLE_SONGS WHERE $TIMESTAMP = (SELECT MAX($TIMESTAMP) FROM $TABLE_SONGS)")
    fun getLatestSong(): Song

    @Query("SELECT * FROM $TABLE_SONGS WHERE $FAVORITE = 1")
    fun getFavorites(): LiveData<List<Song>>
}