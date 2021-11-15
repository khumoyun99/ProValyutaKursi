package com.example.provalyutakursi.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.provalyutakursi.R
import com.example.provalyutakursi.databinding.ItemSpBinding
import com.example.provalyutakursi.models.Currency
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_sp.view.*

class CurrencySpinnerAdapter(private val currencyList:List<Currency>):
    BaseAdapter() {
    override fun getCount(): Int {
        return currencyList.size
    }

    override fun getItem(position: Int): Currency {
        return currencyList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //var itemSpBinding = ItemSpBinding.inflate(LayoutInflater.from(parent?.context))
        val itemView:View = convertView ?: LayoutInflater.from(parent?.context).inflate(
            R.layout.item_sp,
            parent,
            false
        )
        itemView.currency_name_tv.text = currencyList[position].code
        Picasso.get().load(currencyList[position].photoUrl.toString()).into(itemView.img_sp)

        return itemView
    }
}