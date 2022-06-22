package com.haksoy.cryptotracker.data

import com.haksoy.cryptotracker.data.model.CoinMarket
import com.haksoy.cryptotracker.network.ApiInterface
import com.haksoy.cryptotracker.network.Resource
import javax.inject.Inject

class CoinRepository @Inject constructor(private val retrofitInstance: ApiInterface) {

    suspend fun getCoinMarket(): Resource<List<CoinMarket>> {
        val response = retrofitInstance.getCoins()
        return if (response.isSuccessful) {
            if (!response.body().isNullOrEmpty())
                Resource.Success(response.body()!!)
            else
                Resource.Error("Response is empty")
        } else
            Resource.Error(response.message())
    }
}