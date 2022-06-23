package com.haksoy.cryptotracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haksoy.cryptotracker.data.model.CoinMarket

@Dao
interface CoinHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoinData(coinMarket: CoinMarket)

    @Query("Select * from CoinMarket where id=(:coinId)")
    fun getAllCoinHistory(coinId: String): List<CoinMarket>
}