package com.vikram.nowplaying.db

import android.arch.persistence.room.Insert

interface BaseDao<T> {

    @Insert
    fun insert(obj: T)

    @Insert
    fun update(obj: T)

    @Insert
    fun delete(obj: T)
}