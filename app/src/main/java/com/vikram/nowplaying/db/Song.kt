package com.vikram.nowplaying.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.content.Context
import com.vikram.nowplaying.utilities.TABLE_SONGS
import com.vikram.nowplaying.utilities.laymanTime

@Entity(tableName = TABLE_SONGS)
data class Song(var timestamp: Long,
                var songText: String,
                var title: String,
                var artist: String,
                var lat: Double?,
                var lng: Double?,
                var favorite: Int? = 0) {
    @PrimaryKey(autoGenerate = true) var id: Int? = null
}