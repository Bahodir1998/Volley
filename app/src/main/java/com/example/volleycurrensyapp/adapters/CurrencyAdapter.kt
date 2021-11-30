package com.example.volleycurrensyapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.volleycurrensyapp.databinding.CurrencyItemBinding
import com.example.volleycurrensyapp.models.Currency

class CurrencyAdapter(var list: List<Currency>, var myItemClickListener: MyItemClickListener): RecyclerView.Adapter<CurrencyAdapter.Vh>() {

    inner class Vh(var currencyItemBinding: CurrencyItemBinding): RecyclerView.ViewHolder(currencyItemBinding.root){

        fun onBind(currency: Currency){
            currencyItemBinding.tv.setText("${currency.Ccy} - ${currency.CcyNm_UZ}")

            currencyItemBinding.root.setOnClickListener {
                myItemClickListener.onMyItemClick(currency)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(CurrencyItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface MyItemClickListener{
        fun onMyItemClick(currency: Currency)
    }
}