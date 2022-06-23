package com.haksoy.cryptotracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haksoy.cryptotracker.data.model.CoinAlarm
import com.haksoy.cryptotracker.data.model.CoinMarket

@Database(entities = [CoinAlarm::class,CoinMarket::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun coinAlarmDao():CoinAlarmDao
    abstract fun coinHistoryDao():CoinHistoryDao
}