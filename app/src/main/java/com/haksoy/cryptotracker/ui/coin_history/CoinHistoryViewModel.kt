package com.haksoy.cryptotracker.ui.coin_history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haksoy.cryptotracker.data.CoinRepository
import com.haksoy.cryptotracker.data.model.CoinMarket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinHistoryViewModel @Inject constructor(private val coinRepository: CoinRepository) :
    ViewModel() {
    val coinMarketLiveData = MutableLiveData<List<CoinMarket>>()
    fun loadCoinHistory(coinId: String) = viewModelScope.launch {
        val result = coinRepository.getCoinHistory(coinId)
        coinMarketLiveData.postValue(result)
    }

}