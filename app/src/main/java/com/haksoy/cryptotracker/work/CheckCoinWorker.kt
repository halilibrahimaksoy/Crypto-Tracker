package com.haksoy.cryptotracker.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.haksoy.cryptotracker.R
import com.haksoy.cryptotracker.data.CoinRepository
import com.haksoy.cryptotracker.data.model.CoinMarket
import com.haksoy.cryptotracker.db.CoinAlarmDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

@HiltWorker
class CheckCoinWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val coinAlarmDao: CoinAlarmDao,
    private val coinRepository: CoinRepository
) : CoroutineWorker(context, workerParams), CoroutineScope {
    override suspend fun doWork(): Result = coroutineScope {
        val result = coinAlarmDao.getAllAlarms()
        for (coinAlarm in result) {
            val coin: CoinMarket =
                coinRepository.getCoinMarket(coinAlarm.coinId).data!![0]
            if (coinAlarm.minValue > coin.current_price)
                sendNotification(
                    coin.name,
                    context.getString(R.string.app_name),
                    "${coin.name} coin has lower then ${coinAlarm.minValue} $"
                )
            else if (coinAlarm.maxValue < coin.current_price)
                sendNotification(
                    coin.name,
                    context.getString(R.string.app_name),
                    "${coin.name} coin has higher then ${coinAlarm.maxValue} $"
                )
        }
        Log.d("", "doWork run")
        Result.success()
    }


    private fun sendNotification(channelId: String, title: String, message: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //If on Oreo then notification required a notification channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    channelId,
                    System.currentTimeMillis().toString(),
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            notificationManager.createNotificationChannel(channel)
        }
        val notification: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext,
            "default"
        )
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_launcher)
        notificationManager.notify(channelId.hashCode(), notification.build())
    }

}