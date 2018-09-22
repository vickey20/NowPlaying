package com.vikram.nowplaying.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.vikram.nowplaying.db.Song
import com.vikram.nowplaying.repo.SongsRepo
import com.vikram.nowplaying.utilities.splitIntoTitleAndArtist

/**
 * Created by vikramgupta on 2/16/18.
 */

class NotificationService: NotificationListenerService() {

    companion object {
        private const val TAG = "NotificationService"
        private const val GOOGLE_INTELLIGENT_SENSE_PACKAGE_NAME = "com.google.intelligence.sense"
        private const val GGOGLE_INTELLIGENT_SENSE_CHANNEL_NAME = "com.google.intelligence.sense.ambientmusic.MusicNotificationChannel"
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        // Filter only now playing notifications
        if (!sbn?.packageName.equals(GOOGLE_INTELLIGENT_SENSE_PACKAGE_NAME)) return
        if (!sbn?.notification?.channelId.equals(GGOGLE_INTELLIGENT_SENSE_CHANNEL_NAME)) return

        processNotification(sbn)
    }

    private fun processNotification(sbn: StatusBarNotification?) {
        val title = sbn?.notification?.extras?.getString("android.title") ?: return
        var song = getSongObj(sbn.notification.`when`, title)
        saveToDb(song)
    }

    private fun saveToDb(song: Song) {
        SongsRepo.getInstance(applicationContext).saveSong(song)
    }

    private fun getSongObj(timestamp: Long, songText: String): Song {
        var titleAndArtist = songText.splitIntoTitleAndArtist()
        return Song(timestamp, songText, titleAndArtist[0].trim(), titleAndArtist[1].trim())
    }
}