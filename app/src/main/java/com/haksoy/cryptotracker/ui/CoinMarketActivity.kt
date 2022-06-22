package com.haksoy.cryptotracker.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.haksoy.cryptotracker.databinding.ActivityCoinMarketBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinMarketActivity : AppCompatActivity() {
    private val viewModel: CoinMarketViewModel by viewModels()
    private lateinit var coinMarketAdapter: CoinMarketAdapter
    private lateinit var binding: ActivityCoinMarketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        coinMarketAdapter = CoinMarketAdapter()

        binding.coinMarketRecycler.apply {
            adapter = coinMarketAdapter
        }
        viewModel.loadCoins()
        viewModel.coinMarketLiveData.observe(this, Observer {
            coinMarketAdapter.setNewList(it)
        })
        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.progress.observe(this, Observer {
            binding.swiperefresh.isRefreshing = it
        })
        binding.swiperefresh.setOnRefreshListener {
            viewModel.loadCoins()
        }
    }
}