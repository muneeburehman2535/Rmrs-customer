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

class CurrentOrdersAdapter(private val context: Context,private var currentOrderList:ArrayList<CurrentOrderDataClass>): RecyclerView.Adapter<CurrentOrdersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.card_current_orders,parent,false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtOrderName.text=currentOrderList[position].RestaurantName
        holder.txtOrderMenu.text=currentOrderList[position].MenuOrdered[0].Description
        //holder.txtOrderTime.text=currentOrderList[position].time
        holder.txtOrderPrice.text=AppGlobal.mCurrency+currentOrderList[position].TotalAmount.toString()
        if (currentOrderList[position].Status=="NEW_ORDER")
        {
            holder.txtOrderStatus.text=context.getString(R.string.status_pending)
        }
        else{
            holder.txtOrderStatus.text=context.getString(R.string.status_in_progress)
        }
    }

    override fun getItemCount(): Int {
        return currentOrderList.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val txtOrderPrice= itemView.findViewById<TextView>(R.id.txt_order_price_cco)!!
        val txtOrderName= itemView.findViewById<TextView>(R.id.txt_restaurant_name_cco)!!
        val txtOrderMenu= itemView.findViewById<TextView>(R.id.txt_menu_cco)!!
        val txtOrderTime= itemView.findViewById<TextView>(R.id.txt_time_cco)!!
        val txtOrderStatus=itemView.findViewById<TextView>(R.id.txt_order_status_cco)!!
    }

    fun updateList(currentOrderList: ArrayList<CurrentOrderDataClass>){
        this.currentOrderList=currentOrderList
        notifyDataSetChanged()
    }
}