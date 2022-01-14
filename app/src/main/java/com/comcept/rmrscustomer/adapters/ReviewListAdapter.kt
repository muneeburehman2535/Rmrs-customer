package com.comcept.rmrscustomer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.data_class.review.Data
import com.comcept.rmrscustomer.databinding.LayoutReviewListBinding
import com.comcept.rmrscustomer.utilities.AppGlobal
import java.util.*
import kotlin.collections.ArrayList

class ReviewListAdapter(private var reviewList:ArrayList<Data>): RecyclerView.Adapter<ReviewListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.layout_review_list,parent,false)
//        return ViewHolder(itemView)

        val binding:LayoutReviewListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_review_list,parent,false
        )
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.txtCustomerName.text=reviewList[position].CustomerName
//        holder.txtComment.text=reviewList[position].Comment

        holder.bind(reviewList[position])
        val date=reviewList[position].DateCreated.split("T")
        val dateList=date[0].split("-")
        val month=dateList[1].toInt()-1
        val calendar=Calendar.getInstance()
        calendar.set(dateList[0].toInt(),month,dateList[2].toInt())
        val updatedData=AppGlobal.dateToTimeStamp(Date(calendar.timeInMillis),"MMM dd, yyyy")
        holder.binding.txtDateLrl.text=updatedData
        //holder.restaurantRating.rating=reviewList[position].Rating
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    class ViewHolder(var binding: LayoutReviewListBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(data:Data){
            binding.reviewListBinding = data
            binding.executePendingBindings()
        }
//        val txtCustomerName=itemView.findViewById<TextView>(R.id.txt_customer_name_lrl)!!
//        val restaurantRating=itemView.findViewById<RatingBar>(R.id.rb_rating_lrl)!!
//        val txtDate=itemView.findViewById<TextView>(R.id.txt_date_lrl)!!
//        val txtComment=itemView.findViewById<TextView>(R.id.txt_comment_lrl)!!
    }

    fun updateReviewList(reviewList: ArrayList<Data>)
    {
        this.reviewList=reviewList
        notifyDataSetChanged()
    }
}