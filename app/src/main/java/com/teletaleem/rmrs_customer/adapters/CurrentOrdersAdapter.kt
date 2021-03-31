package com.teletaleem.rmrs_customer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.myorders.currentorders.CurrentOrderDataClass
import com.teletaleem.rmrs_customer.utilities.AppGlobal

class CurrentOrdersAdapter(private val context: Context,private val currentOrderList:ArrayList<CurrentOrderDataClass>): RecyclerView.Adapter<CurrentOrdersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.card_current_orders,parent,false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtOrderName.text=currentOrderList[position].name
        holder.txtOrderMenu.text=currentOrderList[position].desc
        holder.txtOrderTime.text=currentOrderList[position].time
        holder.txtOrderPrice.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(currentOrderList[position].price.toDouble())
    }

    override fun getItemCount(): Int {
        return currentOrderList.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val txtOrderPrice= itemView.findViewById<TextView>(R.id.txt_order_price_cco)!!
        val txtOrderName= itemView.findViewById<TextView>(R.id.txt_restaurant_name_cco)!!
        val txtOrderMenu= itemView.findViewById<TextView>(R.id.txt_menu_cco)!!
        val txtOrderTime= itemView.findViewById<TextView>(R.id.txt_time_cco)!!
    }
}