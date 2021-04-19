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
import com.teletaleem.rmrs_customer.data_class.myorders.pastorders.PastOrdersDataClass
import com.teletaleem.rmrs_customer.utilities.AppGlobal

class PastOrdersAdapter(context: Context,private var pastOrdersList:ArrayList<PastOrdersDataClass>): RecyclerView.Adapter<PastOrdersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.card_past_orders,parent,false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtOrderName.text=pastOrdersList[position].RestaurantName
        if (pastOrdersList[position].MenuOrdered.size!=0)
        {
            holder.txtOrderMenu.text=pastOrdersList[position].MenuOrdered[0].Description
        }

        val dateArr=pastOrdersList[position].OrderDate.split("T")
        val timeArr=dateArr[1].split(":")
        holder.txtOrderTime.text="${timeArr[0]}:${timeArr[1]}"
        holder.txtOrderDate.text=dateArr[0]
        holder.txtOrderPrice.text= AppGlobal.mCurrency+ pastOrdersList[position].TotalAmount.toString()
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

    fun updateList(pastOrdersList: ArrayList<PastOrdersDataClass>){
        this.pastOrdersList=pastOrdersList
        notifyDataSetChanged()
    }
}