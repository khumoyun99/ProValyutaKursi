package com.example.provalyutakursi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.provalyutakursi.R
import com.example.provalyutakursi.databinding.ItemRvBinding
import com.example.provalyutakursi.models.Currency
import com.squareup.picasso.Picasso

class CurrencyRvAdapter(val currencyList: List<Currency>,val listener:OnItemClickListener):
    RecyclerView.Adapter<CurrencyRvAdapter.Vh>() {

    inner class Vh(val itemRvBinding: ItemRvBinding):RecyclerView.ViewHolder(itemRvBinding.root){

        fun onBind(currency: Currency){
            itemRvBinding.apply {
                Picasso.get().load(currency.photoUrl).into(flagImg)
                currencyName.text = currency.code
                buyPriceTv.text = currency.nbu_buy_price
                sellPriceTv.text = currency.nbu_cell_price

                calcImg.setOnClickListener {
                    listener.onCalcClick(currency)
                }

                itemRvBinding.currencyItem = currency
                itemRvBinding.executePendingBindings()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_rv,parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(currencyList[position])
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    interface OnItemClickListener{
        fun onCalcClick(currency: Currency)
    }
}