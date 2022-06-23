package com.haksoy.cryptotracker.ui.coin_market

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haksoy.cryptotracker.data.CoinRepository
import com.haksoy.cryptotracker.data.model.CoinMarket
import com.haksoy.cryptotracker.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinMarketViewModel @Inject constructor(private val coinRepository: CoinRepository) :
    ViewModel() {

    val progress = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val coinMarketLiveData = MutableLiveData<List<CoinMarket>>()
    fun loadCoins() = viewModelScope.launch {
        progress.postValue(true)
        val result = coinRepository.getCoinMarket()
        when (result) {
            is Resource.Success -> {
                progress.postValue(false)
                coinMarketLiveData.postValue(result.data!!)
            }
            is Resource.Error -> {
                progress.postValue(false)
                errorMessage.postValue(result.message!!)
            }
        }
    }
}