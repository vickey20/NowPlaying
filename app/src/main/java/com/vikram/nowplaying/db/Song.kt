package com.vikram.nowplaying.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.content.Context
import com.vikram.nowplaying.utilities.laymanTime

@Entity(tableName = "songs")
data class Song(var timestamp: Long,
                var songText: String,
                var title: String,
                var artist: String,
                var lat: Double?,
                var lng: Double?) {
    @PrimaryKey(autoGenerate = true) var id: Int? = null
}