package com.haksoy.cryptotracker.ui.coin_market

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haksoy.cryptotracker.data.model.CoinMarket
import com.haksoy.cryptotracker.databinding.CoinMarketItemBinding

class CoinMarketAdapter(private val itemClickListener: ItemClickListener?) :
    RecyclerView.Adapter<CoinMarketAdapter.MyHolderView>() {

    private var list: List<CoinMarket>? = null


    fun setNewList(data: List<CoinMarket>) {
        this.list = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyHolderView {
        val binding: CoinMarketItemBinding =
            CoinMarketItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolderView(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: MyHolderView, position: Int) {
        holder.bindItems(list!![position])
    }

    override fun getItemCount(): Int {
        return if (list == null)
            0
        else
            list!!.size
    }

    class MyHolderView(
        private val binding: CoinMarketItemBinding,
        private val clickListener: ItemClickListener?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var item: CoinMarket

        init {
            binding.root.setOnClickListener {
                clickListener!!.onItemClicked(item)
            }
        }

        fun bindItems(item: CoinMarket) {
            this.item = item
            Glide.with(binding.root).load(item.image).into(binding.image)
            binding.name.text = item.name
            binding.marketCapRank.text = item.market_cap_rank.toString()
            binding.symbol.text = item.symbol
            binding.currentPrice.text = item.current_price.toString()
        }
    }

    interface ItemClickListener {
        fun onItemClicked(item: CoinMarket)
    }
}