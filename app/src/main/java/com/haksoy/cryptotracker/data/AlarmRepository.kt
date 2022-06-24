package com.haksoy.cryptotracker.data

import com.haksoy.cryptotracker.data.model.CoinAlarm
import com.haksoy.cryptotracker.db.CoinAlarmDao
import javax.inject.Inject

class AlarmRepository @Inject constructor(
    private val coinAlarmDao: CoinAlarmDao
) {
    fun createAlarm(coinAlarm: CoinAlarm) {
        coinAlarmDao.insertCoinAlarm(coinAlarm)
    }

    fun clearAlarm(coinId:String) {
        coinAlarmDao.clearAlarm(coinId)
    }
}