package com.comcept.rmrscustomer.adapters

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
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.data_class.myorders.currentorders.CurrentOrderDataClass
import com.comcept.rmrscustomer.databinding.CardCurrentOrdersBinding
import com.comcept.rmrscustomer.utilities.AppGlobal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

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
    @SuppressLint("SetTextI18n", "NewApi")
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

//


        var updatedOrderData=""
        val dateArr=currentOrderList[position].OrderDate.split("T")
        val date=dateArr[0].split("-")
        val time=dateArr[1].split(":")
        var calendar=Calendar.getInstance()
        calendar.timeZone= TimeZone.getTimeZone("GMT")
        calendar.set(date[0].toInt(),(date[1].toInt()-1),date[2].toInt(),time[0].toInt(),time[1].toInt())
        updatedOrderData= AppGlobal.dateToTimeStamp(Date(calendar.timeInMillis),"MMM dd, hh:mm a").toString()


        holder.binding.txtOrderPriceCco.text= AppGlobal.mCurrency+ AppGlobal.roundTwoPlaces(currentOrderList[position].TotalAmount.toDouble())

        holder.binding.txtDateCco.text=updatedOrderData
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