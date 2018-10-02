package com.vikram.nowplaying.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.vikram.nowplaying.utilities.FAVORITE
import com.vikram.nowplaying.utilities.ID
import com.vikram.nowplaying.utilities.TABLE_SONGS
import com.vikram.nowplaying.utilities.TIMESTAMP

@Dao
interface SongDao {

    @Insert
    fun insert(song: Song)

    @Insert
    fun update(song: Song)

    @Insert
    fun delete(song: Song)

    @Query("SELECT * FROM $TABLE_SONGS ORDER BY $ID DESC")
    fun getAllSongs(): LiveData<List<Song>>

    @Query("SELECT * FROM $TABLE_SONGS WHERE $TIMESTAMP = (SELECT MAX($TIMESTAMP) FROM $TABLE_SONGS)")
    fun getLatestSong(): Song

    @Query("SELECT * FROM $TABLE_SONGS WHERE $FAVORITE = 1")
    fun getFavorites(): LiveData<List<Song>>
}