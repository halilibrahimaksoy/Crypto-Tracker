package com.haksoy.cryptotracker.data

import com.haksoy.cryptotracker.data.model.CoinAlarm
import com.haksoy.cryptotracker.db.CoinAlarmDao
import javax.inject.Inject

class AlarmRepository @Inject constructor(
    private val coinAlarmDao: CoinAlarmDao
) {
    suspend fun createAlarm(coinAlarm: CoinAlarm) {
        println("")
        coinAlarmDao.insertCoinAlarm(coinAlarm)
    }
}