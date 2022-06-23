package com.haksoy.cryptotracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.haksoy.cryptotracker.R
import com.haksoy.cryptotracker.data.model.CoinMarket
import com.haksoy.cryptotracker.databinding.FragmentCoinMarketBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinMarketFragment : Fragment() {
    private val viewModel: CoinMarketViewModel by viewModels()
    private lateinit var coinMarketAdapter: CoinMarketAdapter
    private lateinit var binding: FragmentCoinMarketBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoinMarketBinding.inflate(inflater, container, false)
        coinMarketAdapter = CoinMarketAdapter(object : CoinMarketAdapter.ItemClickListener {
            override fun onItemClicked(item: CoinMarket) {
                findNavController().navigate(R.id.action_market_to_alarm, Bundle().apply {
                    putSerializable("Coin", item)
                })
            }
        })

        binding.coinMarketRecycler.apply {
            adapter = coinMarketAdapter
        }
        viewModel.loadCoins()
        viewModel.coinMarketLiveData.observe(viewLifecycleOwner, Observer {
            coinMarketAdapter.setNewList(it)
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.progress.observe(viewLifecycleOwner, Observer {
            binding.swiperefresh.isRefreshing = it
        })
        binding.swiperefresh.setOnRefreshListener {
            viewModel.loadCoins()
        }
        return binding.root
    }

}