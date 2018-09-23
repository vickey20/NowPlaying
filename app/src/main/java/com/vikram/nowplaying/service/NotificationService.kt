package com.vikram.nowplaying.service

import android.location.Location
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.vikram.nowplaying.db.Song
import com.vikram.nowplaying.repo.SongsRepo
import com.vikram.nowplaying.utilities.GOOGLE_INTELLIGENT_SENSE_CHANNEL_NAME
import com.vikram.nowplaying.utilities.GOOGLE_INTELLIGENT_SENSE_PACKAGE_NAME
import com.vikram.nowplaying.utilities.getCurrentLocation
import com.vikram.nowplaying.utilities.splitIntoTitleAndArtist
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

/**
 * Created by vikramgupta on 2/16/18.
 */

class NotificationService: NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        // Filter only now playing notifications
        if (!sbn?.packageName.equals(GOOGLE_INTELLIGENT_SENSE_PACKAGE_NAME)) return
        if (!sbn?.notification?.channelId.equals(GOOGLE_INTELLIGENT_SENSE_CHANNEL_NAME)) return

        processNotification(sbn)
    }

    private fun processNotification(sbn: StatusBarNotification?) {
        val title = sbn?.notification?.extras?.getString("android.title") ?: return
        GlobalScope.launch {
            val location = async { getCurrentLocation(applicationContext) }.await()
            var song = getSongObj(sbn.notification.`when`, title, location)
            saveToDb(song)
        }
    }

    private fun saveToDb(song: Song) {
        SongsRepo.getInstance(applicationContext).saveSong(song)
    }

    private fun getSongObj(timestamp: Long, songText: String, location: Location?): Song {
        val titleAndArtist = songText.splitIntoTitleAndArtist()
        return Song(timestamp, songText, titleAndArtist[0], titleAndArtist[1], location?.latitude, location?.longitude)
    }
}