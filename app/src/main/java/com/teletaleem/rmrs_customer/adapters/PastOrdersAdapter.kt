package com.teletaleem.rmrs_customer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.myorders.pastorders.PastOrdersDataClass
import com.teletaleem.rmrs_customer.utilities.AppGlobal

class PastOrdersAdapter(context: Context,private val pastOrdersList:ArrayList<PastOrdersDataClass>): RecyclerView.Adapter<PastOrdersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.card_past_orders,parent,false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtOrderName.text=pastOrdersList[position].name
        holder.txtOrderMenu.text=pastOrdersList[position].desc
        holder.txtOrderTime.text=pastOrdersList[position].time
        holder.txtOrderDate.text=pastOrdersList[position].date
        holder.txtOrderPrice.text= AppGlobal.mCurrency+ AppGlobal.roundTwoPlaces(pastOrdersList[position].price.toDouble())
    }

    override fun getItemCount(): Int {
        return pastOrdersList.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val txtOrderPrice= itemView.findViewById<TextView>(R.id.txt_order_price_cpo)!!
        val txtOrderName= itemView.findViewById<TextView>(R.id.txt_restaurant_name_cpo)!!
        val txtOrderMenu= itemView.findViewById<TextView>(R.id.txt_menu_cpo)!!
        val txtOrderTime= itemView.findViewById<TextView>(R.id.txt_time_cpo)!!
        val txtOrderDate= itemView.findViewById<TextView>(R.id.txt_date_cpo)!!
    }
}