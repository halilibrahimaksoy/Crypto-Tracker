package com.haksoy.cryptotracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CoinAlarm(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val coinId: String,
    val minValue: Double,
    val maxValue: Double
)