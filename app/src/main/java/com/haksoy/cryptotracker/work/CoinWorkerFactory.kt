package com.haksoy.cryptotracker.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.haksoy.cryptotracker.data.CoinRepository
import com.haksoy.cryptotracker.db.CoinAlarmDao
import com.haksoy.cryptotracker.db.CoinHistoryDao
import javax.inject.Inject

class CoinWorkerFactory @Inject constructor(
    private val coinAlarmDao: CoinAlarmDao,
    private val coinHistoryDao: CoinHistoryDao,
    private val coinRepository: CoinRepository
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker =
        CheckCoinWorker(appContext, workerParameters, coinAlarmDao, coinHistoryDao, coinRepository)
}