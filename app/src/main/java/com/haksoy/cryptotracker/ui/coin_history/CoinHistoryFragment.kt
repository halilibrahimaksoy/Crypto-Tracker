package com.haksoy.cryptotracker.ui.coin_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.haksoy.cryptotracker.data.model.CoinMarket
import com.haksoy.cryptotracker.databinding.FragmentCoinHistoryBinding
import com.haksoy.cryptotracker.ui.coin_market.CoinMarketAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinHistoryFragment : Fragment() {
    private lateinit var binding: FragmentCoinHistoryBinding
    private val viewModel: CoinHistoryViewModel by viewModels()
    private lateinit var coinMarket: CoinMarket
    private lateinit var coinMarketAdapter: CoinMarketAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            coinMarket = it.get("Coin") as CoinMarket
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoinHistoryBinding.inflate(layoutInflater, container, false)
        binding.title.text = "${coinMarket.name} history"
        coinMarketAdapter = CoinMarketAdapter(null)
        binding.coinMarketRecycler.apply {
            adapter = coinMarketAdapter
        }
        viewModel.loadCoinHistory(coinMarket.id)
        viewModel.coinMarketLiveData.observe(viewLifecycleOwner, Observer {
            coinMarketAdapter.setNewList(it)
        })
        // Inflate the layout for this fragment
        return binding.root
    }

}