package com.haksoy.cryptotracker.data

import com.haksoy.cryptotracker.data.model.CoinMarket
import com.haksoy.cryptotracker.db.CoinHistoryDao
import com.haksoy.cryptotracker.network.ApiInterface
import com.haksoy.cryptotracker.network.Resource
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val retrofitInstance: ApiInterface,
    private val coinHistoryDao: CoinHistoryDao
) {

    suspend fun getCoinMarket(): Resource<List<CoinMarket>> {
        val response = retrofitInstance.getCoinsMarketData()
        return if (response.isSuccessful) {
            if (!response.body().isNullOrEmpty())
                Resource.Success(response.body()!!)
            else
                Resource.Error("Response is empty")
        } else
            Resource.Error(response.message())
    }
    suspend fun getCoinMarket( id:String): Resource<List<CoinMarket>> {
        val response = retrofitInstance.getCoinMarketData(id)
        return if (response.isSuccessful) {
            if (!response.body().isNullOrEmpty())
                Resource.Success(response.body()!!)
            else
                Resource.Error("Response is empty")
        } else
            Resource.Error(response.message())
    }

    suspend fun getCoinHistory(coinId: String): List<CoinMarket> {
        return coinHistoryDao.getAllCoinHistory(coinId)

    }
}