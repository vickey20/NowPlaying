package com.vikram.nowplaying.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.vikram.nowplaying.R
import com.vikram.nowplaying.db.Song
import com.vikram.nowplaying.repo.SongsRepo

/**
 * Created by vikramgupta on 2/16/18.
 */

class NotificationService: NotificationListenerService() {

    companion object {
        private const val TAG = "NotificationService"
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        val packageName = sbn?.packageName

        // Filter only now playing notifications
        if (!packageName.equals(getString(R.string.now_playing_package_name))) return

        processNotification(sbn)
    }

    private fun processNotification(sbn: StatusBarNotification?) {
        val ticker = sbn?.notification?.tickerText
        val extras = sbn?.notification?.extras
        val title = extras?.getString("android.title")
        val text = extras?.getString("android.text").toString()

        Log.i(TAG, "packageName: $packageName, ticker: $ticker, title: $title, text: $text")

        if (title == null) return

        var song = getSongObj(title)

        saveToDb(song)
    }

    fun saveToDb(song: Song) {
        SongsRepo.getInstance(applicationContext).saveSong(song)
    }

    private fun getSongObj(title: String): Song {
        var titleAndArtist = title.split("by")
        Log.d(TAG, "title: ${titleAndArtist[0]?.trim()} artist: ${titleAndArtist[1].trim()}")

        return Song(titleAndArtist[0].trim(), titleAndArtist[1].trim())
    }
}