package com.vikram.nowplaying.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "songs")
data class Song(var title: String,
                var artist: String) {
    @PrimaryKey(autoGenerate = true) var id: Int? = null
}