package com.haksoy.cryptotracker.network

import com.haksoy.cryptotracker.data.model.CoinMarketResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false")
    suspend fun getCoins(): Response<CoinMarketResponse>
}