package com.haksoy.cryptotracker.di

import android.content.Context
import androidx.room.Room
import com.haksoy.cryptotracker.R
import com.haksoy.cryptotracker.alarm.AlarmRepository
import com.haksoy.cryptotracker.db.AppDatabase
import com.haksoy.cryptotracker.db.CoinAlarmDao
import com.haksoy.cryptotracker.db.CoinHistoryDao
import com.haksoy.cryptotracker.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    val BASE_URL = "https://api.coingecko.com/api/v3/"

    @Singleton
    @Provides
    fun getRetrofitInstance(): ApiInterface {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun getAlarmRepository(
        coinAlarmDao: CoinAlarmDao
    ): AlarmRepository {
        return AlarmRepository(coinAlarmDao)
    }

    @Provides
    fun provideCoinAlarmDao(appDatabase: AppDatabase): CoinAlarmDao {
        return appDatabase.coinAlarmDao()
    }

    @Provides
    fun provideCoinHistoryDao(appDatabase: AppDatabase): CoinHistoryDao {
        return appDatabase.coinHistoryDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            appContext.getString(R.string.app_name)
        ).allowMainThreadQueries().build()
    }
}