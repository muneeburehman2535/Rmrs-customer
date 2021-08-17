package com.teletaleem.rmrs_customer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.myorders.currentorders.CurrentOrderDataClass
import com.teletaleem.rmrs_customer.databinding.CardCurrentOrdersBinding
import com.teletaleem.rmrs_customer.utilities.AppGlobal

class CurrentOrdersAdapter(private val context: Context,private var currentOrderList:ArrayList<CurrentOrderDataClass>): RecyclerView.Adapter<CurrentOrdersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.card_current_orders,parent,false)
//        return ViewHolder(itemView)

        val binding:CardCurrentOrdersBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.card_current_orders,parent,false
        )
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.txtOrderName.text=currentOrderList[position].RestaurantName
//        holder.txtOrderMenu.text=currentOrderList[position].MenuOrdered[0].Description

        holder.bind(currentOrderList[position])
      //  holder.binding.txtTimeCco.text=currentOrderList[position].OrderDate
        holder.binding.txtOrderPriceCco.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(currentOrderList[position].TotalAmount.toDouble())
        if (currentOrderList[position].Status=="NEW_ORDER")
        {
            holder.binding.txtOrderStatusCco.text=context.getString(R.string.status_pending)
            holder.binding.txtOrderStatusCco.setTextColor(context.getColor(R.color.color_pending))
        }
        else{
            holder.binding.txtOrderStatusCco.text=context.getString(R.string.status_in_progress)
            holder.binding.txtOrderStatusCco.setTextColor(context.getColor(R.color.colorAccent))
        }

        val dateArr=currentOrderList[position].OrderDate.split("T")
        val timeArr=dateArr[1].split(":")
        holder.binding.txtTimeCco.text="${timeArr[0]}:${timeArr[1]}"
        holder.binding.txtDateCco.text=dateArr[0]
        holder.binding.txtOrderPriceCco.text= AppGlobal.mCurrency+ AppGlobal.roundTwoPlaces(currentOrderList[position].TotalAmount.toDouble())

    }

    override fun getItemCount(): Int {
        return currentOrderList.size
    }

    class ViewHolder(var binding: CardCurrentOrdersBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(data:CurrentOrderDataClass){
            binding.currentOrderBinding = data
            binding.executePendingBindings()
        }

//        val txtOrderPrice= itemView.findViewById<TextView>(R.id.txt_order_price_cco)!!
//        val txtOrderName= itemView.findViewById<TextView>(R.id.txt_restaurant_name_cco)!!
//        val txtOrderMenu= itemView.findViewById<TextView>(R.id.txt_menu_cco)!!
//        val txtOrderTime= itemView.findViewById<TextView>(R.id.txt_time_cco)!!
//        val txtOrderStatus=itemView.findViewById<TextView>(R.id.txt_order_status_cco)!!
    }

    fun updateList(currentOrderList: ArrayList<CurrentOrderDataClass>){
        this.currentOrderList=currentOrderList
        notifyDataSetChanged()
    }
}