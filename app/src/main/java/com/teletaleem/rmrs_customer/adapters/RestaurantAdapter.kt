package com.teletaleem.rmrs_customer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.home.restaurants.Restaurants
import com.teletaleem.rmrs_customer.utilities.AppGlobal

class RestaurantAdapter(private val requireContext: Context, private var restaurantsList: ArrayList<Restaurants>) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val contactView=LayoutInflater.from(parent.context).inflate(R.layout.layout_restaurant,parent,false)
        return ViewHolder(contactView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        AppGlobal.loadImageIntoGlide(restaurantsList[position].Image,holder.imgRestaurant,requireContext)

        holder.txtRestaurantName.text=restaurantsList[position].RestaurantName
        holder.txtAddress.text=restaurantsList[position].Address
        holder.txtTotalRating.text=restaurantsList[position].Rating
        holder.txtTotalRatingCount.text="(${restaurantsList[position].RatingCount})"

    }

    override fun getItemCount(): Int {
       return restaurantsList.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val imgRestaurant= itemView.findViewById<ImageView>(R.id.img_profile)!!
        val txtRestaurantName= itemView.findViewById<TextView>(R.id.txt_name_lrestaurant)!!
        val txtAddress= itemView.findViewById<TextView>(R.id.txt_address_lrestaurant)!!
        val txtTotalRatingCount= itemView.findViewById<TextView>(R.id.txt_points_lrestaurant)!!
        val txtTotalRating= itemView.findViewById<TextView>(R.id.txt_rating_lrestaurant)!!
    }

    fun updateRestaurantsList(restaurantsList: ArrayList<Restaurants>){
        this.restaurantsList=restaurantsList
        notifyDataSetChanged()
    }


}