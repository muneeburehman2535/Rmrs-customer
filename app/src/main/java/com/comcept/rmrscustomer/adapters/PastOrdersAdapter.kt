package com.comcept.rmrscustomer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.data_class.myorders.currentorders.CurrentOrderDataClass
import com.comcept.rmrscustomer.data_class.myorders.pastorders.PastOrdersDataClass
import com.comcept.rmrscustomer.databinding.CardPastOrdersBinding
import com.comcept.rmrscustomer.utilities.AppGlobal
import java.util.*
import kotlin.collections.ArrayList

class PastOrdersAdapter(context: Context,private var pastOrdersList:ArrayList<PastOrdersDataClass>): RecyclerView.Adapter<PastOrdersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.card_past_orders,parent,false)
//        return ViewHolder(itemView)

        val binding:CardPastOrdersBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.card_past_orders,parent,false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       // holder.txtOrderName.text=pastOrdersList[position].RestaurantName

        holder.bind(pastOrdersList[position])
        if (pastOrdersList[position].MenuOrdered.size!=0)
        {
            holder.binding.txtMenuCpo.text=pastOrdersList[position].MenuOrdered[0].Description


        }

//        val dateArr=pastOrdersList[position].OrderDate.split("T")
//        val timeArr=dateArr[1].split(":")
//        holder.binding.txtTimeCpo.text="${timeArr[0]}:${timeArr[1]}"
//        holder.binding.txtDateCpo.text=dateArr[0]

        var updatedOrderData=""
        val dateArr=pastOrdersList[position].OrderDate.split("T")
        val date=dateArr[0].split("-")
        val time=dateArr[1].split(":")
        var calendar= Calendar.getInstance()
        calendar.timeZone= TimeZone.getTimeZone("GMT")
        calendar.set(date[0].toInt(),(date[1].toInt()-1),date[2].toInt(),time[0].toInt(),time[1].toInt())
        updatedOrderData= AppGlobal.dateToTimeStamp(Date(calendar.timeInMillis),"MMM dd, hh:mm a").toString()
        holder.binding.txtDateCpo.text=updatedOrderData
        holder.binding.txtOrderPriceCpo.text= AppGlobal.mCurrency+ AppGlobal.roundTwoPlaces(pastOrdersList[position].TotalAmount.toDouble())
    }

    override fun getItemCount(): Int {
        return pastOrdersList.size
    }

    class ViewHolder(var binding: CardPastOrdersBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(data:PastOrdersDataClass){

            binding.pastOrderBinding = data
            binding.executePendingBindings()

        }

//        val txtOrderPrice= itemView.findViewById<TextView>(R.id.txt_order_price_cpo)!!
//        val txtOrderName= itemView.findViewById<TextView>(R.id.txt_restaurant_name_cpo)!!
//        val txtOrderMenu= itemView.findViewById<TextView>(R.id.txt_menu_cpo)!!
//        val txtOrderTime= itemView.findViewById<TextView>(R.id.txt_time_cpo)!!
//        val txtOrderDate= itemView.findViewById<TextView>(R.id.txt_date_cpo)!!

    }

    fun updateList(pastOrdersList: ArrayList<PastOrdersDataClass>){
        this.pastOrdersList=pastOrdersList
        notifyDataSetChanged()
    }
}