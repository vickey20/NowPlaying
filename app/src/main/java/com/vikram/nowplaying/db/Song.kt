package com.vikram.nowplaying.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.vikram.nowplaying.utilities.laymanTime

@Entity(tableName = "songs")
data class Song(var timestamp: Long,
                var songText: String,
                var title: String,
                var artist: String) {
    @PrimaryKey(autoGenerate = true) var id: Int? = null

    fun getLaymanTime(): String {
        return timestamp.laymanTime()
    }
}