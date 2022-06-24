package com.haksoy.cryptotracker

import android.app.Application
import androidx.work.Configuration
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.haksoy.cryptotracker.work.CheckCoinWorker
import com.haksoy.cryptotracker.work.CoinWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MainApp:Application() ,Configuration.Provider {

    @Inject
    lateinit var myWorkerFactory: CoinWorkerFactory
    override fun onCreate() {
        super.onCreate()
        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(CheckCoinWorker::class.java, 1, TimeUnit.MINUTES)
                .build()

        val workManager = WorkManager.getInstance(this)

        workManager.enqueue(periodicWorkRequest)
    }

    override fun getWorkManagerConfiguration(): Configuration=   Configuration.Builder()
        .setWorkerFactory(myWorkerFactory)
        .build()

}