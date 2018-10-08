package com.vikram.nowplaying.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context
import com.vikram.nowplaying.utilities.DATABASE_NAME
import com.vikram.nowplaying.utilities.FAVORITE
import com.vikram.nowplaying.utilities.TABLE_SONGS

@Database(entities = arrayOf(Song::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songsDao(): SongDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance?: synchronized(this) {
                instance?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .addMigrations(MIGRATION_1_2)
                    .build()
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE $TABLE_SONGS ADD COLUMN $FAVORITE INTEGER DEFAULT 0")
            }
        }
    }
}