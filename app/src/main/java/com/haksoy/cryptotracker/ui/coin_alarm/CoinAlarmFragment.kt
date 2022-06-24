package com.haksoy.cryptotracker.ui.coin_alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.haksoy.cryptotracker.R
import com.haksoy.cryptotracker.data.model.CoinMarket
import com.haksoy.cryptotracker.databinding.FragmentCoinAlarmBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinAlarmFragment : Fragment() {
    private lateinit var coinMarket: CoinMarket
    private lateinit var binding: FragmentCoinAlarmBinding
    private val viewModel: CoinAlarmViewModel by viewModels()

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
        binding = FragmentCoinAlarmBinding.inflate(layoutInflater, container, false)
        Glide.with(binding.root).load(coinMarket.image).into(binding.image)
        binding.name.text = coinMarket.name
        binding.symbol.text = coinMarket.symbol
        binding.currentPrice.text = coinMarket.current_price.toString()

        binding.setAlarm.setOnClickListener {
            if (validate()) {
                createAlarm()
            }
        }
        binding.history.setOnClickListener {
            findNavController().navigate(R.id.action_alarm_to_history, Bundle().apply {
                putSerializable("Coin", coinMarket)
            })
        }
        binding.deleteAlarm.setOnClickListener {
viewModel.clearAlarm(coinMarket.id)
        }
        viewModel.message.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
        return binding.root
    }

    private fun createAlarm() {
        viewModel.createAlarm(
            coinMarket,
            binding.edtMinValue.text.toString().toDouble(),
            binding.edtMaxValue.text.toString().toDouble()
        )
    }

    private fun validate(): Boolean =
        binding.edtMaxValue.text.isNotEmpty() || binding.edtMinValue.text.isNotEmpty()
}