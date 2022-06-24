package com.haksoy.cryptotracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.haksoy.cryptotracker.data.model.CoinAlarm

@Dao
interface CoinAlarmDao {
    @Insert
    fun insertCoinAlarm(coinAlarm: CoinAlarm)

    @Query("Delete  from CoinAlarm where coinId=(:coinId)")
    fun clearAlarm(coinId: String)

    @Query("Select * from CoinAlarm")
    fun getAllAlarms(): List<CoinAlarm>
}