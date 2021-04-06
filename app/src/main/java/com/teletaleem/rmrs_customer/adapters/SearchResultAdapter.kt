package com.teletaleem.rmrs_customer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.restaurant.RestaurantDataClass

class SearchResultAdapter(val context:Context,private val searchResultList:ArrayList<RestaurantDataClass>):RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.layout_restaurant_search,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txtRestaurantName.text=searchResultList[position].restaurant_name
        holder.txtRestaurantAddress.text=searchResultList[position].address
        holder.txtRating.text=searchResultList[position].rating
        holder.txtTotalRatingCount.text=searchResultList[position].rating_number
    }

    override fun getItemCount(): Int {
       return searchResultList.size
    }


    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val txtRestaurantName=itemView.findViewById<TextView>(R.id.txt_name_lrestaurant)
        val txtRestaurantAddress=itemView.findViewById<TextView>(R.id.txt_address_lrestaurant)
        val txtRating=itemView.findViewById<TextView>(R.id.txt_rating_lrestaurant)
        val txtTotalRatingCount=itemView.findViewById<TextView>(R.id.txt_points_lrestaurant)
        val imgRestaurantImage=itemView.findViewById<ImageView>(R.id.img_profile)
    }
}