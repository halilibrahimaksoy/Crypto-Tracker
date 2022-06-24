package com.haksoy.cryptotracker.ui.coin_alarm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haksoy.cryptotracker.data.AlarmRepository
import com.haksoy.cryptotracker.data.model.CoinAlarm
import com.haksoy.cryptotracker.data.model.CoinMarket
import com.haksoy.cryptotracker.db.CoinHistoryDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinAlarmViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val coinHistoryDao: CoinHistoryDao
) :
    ViewModel() {

    val message = MutableLiveData<String>()
    fun createAlarm(coinMarket: CoinMarket, minValue: Double, maxValue: Double) =
        viewModelScope.launch {
            alarmRepository.createAlarm(
                CoinAlarm(
                    id = System.currentTimeMillis().toInt(),
                    coinId = coinMarket.id,
                    minValue = minValue,
                    maxValue = maxValue
                )
            )
            coinHistoryDao.insertCoinData(coinMarket)
            message.postValue("Alarm has been created")
        }

}