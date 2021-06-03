package com.teletaleem.rmrs_customer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.reservation.Result
import com.teletaleem.rmrs_customer.utilities.AppGlobal
import java.util.*
import kotlin.collections.ArrayList

class MyReservationsAdapter(private val mContext:Context,private var reservationList:ArrayList<Result>): RecyclerView.Adapter<MyReservationsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.layout_reservation,parent,false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txtCustomerName.text=reservationList[position].CustomerName
        holder.txtCustomerMobile.text=reservationList[position].MobileNumber
        holder.txtRestaurantName.text=reservationList[position].RestaurantName
        if (reservationList[position].Status=="AWAITING_ACCEPTANCE")
        {
            holder.txtReservationStatus.text=mContext.getString(R.string.status_pending)
        }
        else if (reservationList[position].Status=="RESERVED")
        {
            holder.txtReservationStatus.text=mContext.getString(R.string.title_reserved)
        }
        else if (reservationList[position].Status=="REJECTED")
        {
            holder.txtReservationStatus.text=mContext.getString(R.string.title_rejected)
        }
        else{
            holder.txtReservationStatus.text=mContext.getString(R.string.title_confirmed)
        }

        val dateArr=reservationList[position].ReservationTime.split("T")
        val date=dateArr[0].split("-")
        val time=dateArr[1].split(":")
        val month=date[1].toInt()-1
        val calendar=Calendar.getInstance()
        calendar.set(date[0].toInt(),month,date[2].toInt(),time[0].toInt(),time[1].toInt())
        holder.txtDate.text=AppGlobal.dateToTimeStamp(Date(calendar.timeInMillis),"MMM dd, yyyy hh:mm a")
        holder.txtNoOfPeople.text="${reservationList[position].NumberOfPeople} People"

    }

    override fun getItemCount(): Int {
        return reservationList.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val txtCustomerName=itemView.findViewById<TextView>(R.id.txt_customer_name_lr)!!
        val txtCustomerMobile=itemView.findViewById<TextView>(R.id.txt_mobile_number_lr)!!
        val txtDate=itemView.findViewById<TextView>(R.id.txt_date_lr)!!
        val txtRestaurantName=itemView.findViewById<TextView>(R.id.txt_restaurant_name_lr)!!
        val txtReservationStatus=itemView.findViewById<TextView>(R.id.txt_reservation_status_lr)!!
        val txtNoOfPeople=itemView.findViewById<TextView>(R.id.txt_no_of_people_lr)!!
    }

    fun updateReservationList(reservationList:ArrayList<Result>){
        this.reservationList=reservationList
        notifyDataSetChanged()
    }
}