package com.summer.newsapp.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.summer.newsapp.service.FetchNewsService

class ServiceAlarmHandler(private val mContext: Context) {

    private val TAG = "ServiceAlarmHandler"

    fun setAlarmManager() {
        Log.e(TAG, "MyAlarmManager Started")
        val startServiceIntent = Intent(mContext, FetchNewsService::class.java)
        val sender = PendingIntent.getForegroundService(
            mContext,
            Constants.ALARM_PENDING_INTENT_REQUEST_CODE,
            startServiceIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + Constants.ALARM_TRIGGER,
            Constants.ALARM_TRIGGER,
            sender
        )
    }

    fun cancelAlarmManager() {
        val startServiceIntent = Intent(mContext, FetchNewsService::class.java)
        val sender = PendingIntent.getForegroundService(
            mContext,
            Constants.ALARM_PENDING_INTENT_REQUEST_CODE,
            startServiceIntent,
            0
        )
        val alarmManager = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(sender)
    }
}