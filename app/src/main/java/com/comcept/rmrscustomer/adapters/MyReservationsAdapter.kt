package com.comcept.rmrscustomer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.data_class.reservation.Result
import com.comcept.rmrscustomer.databinding.LayoutReservationBinding
import com.comcept.rmrscustomer.utilities.AppGlobal
import java.util.*
import kotlin.collections.ArrayList

class MyReservationsAdapter(private val mContext:Context,private var reservationList:ArrayList<Result>): RecyclerView.Adapter<MyReservationsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.layout_reservation,parent,false)
//        return ViewHolder(itemView)

        val binding:LayoutReservationBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_reservation,parent,false
        )
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        holder.txtCustomerName.text=reservationList[position].CustomerName
//        holder.txtCustomerMobile.text=reservationList[position].MobileNumber
//        holder.txtRestaurantName.text=reservationList[position].RestaurantName

        holder.bind(reservationList[position])

        when (reservationList[position].Status) {
            "AWAITING_ACCEPTANCE" -> {
                holder.binding.txtReservationStatusLr.text=mContext.getString(R.string.status_pending)
                holder.binding.txtReservationStatusLr.setTextColor(mContext.getColor(R.color.color_pending))
            }
            "RESERVED" -> {
                holder.binding.txtReservationStatusLr.text=mContext.getString(R.string.title_reserved)
                holder.binding.txtReservationStatusLr.setTextColor(mContext.getColor(R.color.color_reserved))
            }
            "REJECTED" -> {
                holder.binding.txtReservationStatusLr.text=mContext.getString(R.string.title_rejected)
                holder.binding.txtReservationStatusLr.setTextColor(mContext.getColor(R.color.color_rejected))
            }
            else -> {
                holder.binding.txtReservationStatusLr.text=mContext.getString(R.string.title_confirmed)
                holder.binding.txtReservationStatusLr.setTextColor(mContext.getColor(R.color.colorAccent))
            }
        }

        val dateArr=reservationList[position].ReservationTime.split("T")
        val date=dateArr[0].split("-")
        val time=dateArr[1].split(":")
        val month=date[1].toInt()-1
        val calendar=Calendar.getInstance()
        calendar.set(date[0].toInt(),month,date[2].toInt(),time[0].toInt(),time[1].toInt())
        holder.binding.txtDateLr.text=AppGlobal.dateToTimeStamp(Date(calendar.timeInMillis),"MMM dd, yyyy hh:mm a")
        holder.binding.txtNoOfPeopleLr.text="${reservationList[position].NumberOfPeople} People"

    }

    override fun getItemCount(): Int {
        return reservationList.size
    }

    class ViewHolder(val binding: LayoutReservationBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(data : Result){

            binding.reservationBinding = data
            binding.executePendingBindings()
        }


//        val txtCustomerName=itemView.findViewById<TextView>(R.id.txt_customer_name_lr)!!
//        val txtCustomerMobile=itemView.findViewById<TextView>(R.id.txt_mobile_number_lr)!!
//        val txtDate=itemView.findViewById<TextView>(R.id.txt_date_lr)!!
//        val txtRestaurantName=itemView.findViewById<TextView>(R.id.txt_restaurant_name_lr)!!
//        val txtReservationStatus=itemView.findViewById<TextView>(R.id.txt_reservation_status_lr)!!
//        val txtNoOfPeople=itemView.findViewById<TextView>(R.id.txt_no_of_people_lr)!!
    }

    fun updateReservationList(reservationList:ArrayList<Result>){
        this.reservationList=reservationList
        notifyDataSetChanged()
    }
}