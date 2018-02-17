package com.vikram.nowplaying.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.vikram.nowplaying.R

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

        val ticker = sbn?.notification?.tickerText
        val extras = sbn?.notification?.extras
        val title = extras?.getString("android.title")
        val text = extras?.getString("android.text").toString()

        Log.i(TAG, "packageName: " + packageName)
        Log.i(TAG, "ticker: " + ticker)
        Log.i(TAG, "title: " + title)
        Log.i(TAG, "text: " + text)
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
    }
}