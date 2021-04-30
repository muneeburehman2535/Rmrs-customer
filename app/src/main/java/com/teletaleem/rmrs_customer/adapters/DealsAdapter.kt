package com.teletaleem.rmrs_customer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.home.restaurants.Deals
import com.teletaleem.rmrs_customer.utilities.AppGlobal

class DealsAdapter(private val requireContext: Context,private var dealsList: ArrayList<Deals>) : RecyclerView.Adapter<DealsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealsAdapter.ViewHolder {
        val contactView=LayoutInflater.from(parent.context).inflate(R.layout.layout_deals,parent,false)
        return ViewHolder(contactView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DealsAdapter.ViewHolder, position: Int) {

        AppGlobal.loadImageIntoGlide(dealsList[position].Image,holder.imgProfile,requireContext)

        holder.txtRestaurantName.text=dealsList[position].name
        holder.txtRestaurantAddress.text=dealsList[position].address
        holder.txtRating.text=dealsList[position].rating
        holder.txtRatingCount.text="(${dealsList[position].rating_count})"
        holder.txtDeal.text=dealsList[position].discount
    }

    override fun getItemCount(): Int {
        return dealsList.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val imgProfile=itemView.findViewById<ImageView>(R.id.img_profile_deals)!!
        val txtDeal=itemView.findViewById<TextView>(R.id.txt_discount_deals)!!
        val txtRestaurantName=itemView.findViewById<TextView>(R.id.txt_name_ldeals)!!
        val txtRestaurantAddress=itemView.findViewById<TextView>(R.id.txt_address_ldeals)!!
        val txtRatingCount=itemView.findViewById<TextView>(R.id.txt_points_ldeals)!!
        val txtRating=itemView.findViewById<TextView>(R.id.txt_rating_ldeals)!!
    }

    fun updateList(dealsList: ArrayList<Deals>){
        this.dealsList=dealsList
        notifyDataSetChanged()
    }
}