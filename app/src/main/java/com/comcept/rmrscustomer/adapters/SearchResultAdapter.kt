package com.comcept.rmrscustomer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.data_class.home.restaurants.Restaurants
import com.comcept.rmrscustomer.data_class.restaurant.RestaurantDataClass
import com.comcept.rmrscustomer.databinding.LayoutRestaurantSearchBinding
import com.comcept.rmrscustomer.utilities.AppGlobal

class SearchResultAdapter(val context:Context,private var searchResultList:ArrayList<Restaurants>):RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.layout_restaurant_search,parent,false)
//        return ViewHolder(itemView)

        val binding: LayoutRestaurantSearchBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_restaurant_search, parent, false
        )
        return ViewHolder(binding)


    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        holder.txtRestaurantName.text=searchResultList[position].RestaurantName
//        holder.txtRestaurantAddress.text=searchResultList[position].Address
//        holder.txtRating.text=searchResultList[position].Rating

        holder.bind(searchResultList[position])


        //holder.txtTotalRatingCount.text="(${searchResultList[position].RatingCount})"
        holder.binding.txtPointsLrestaurant.text="(${searchResultList[position].RatingCount})"
        AppGlobal.loadImageIntoGlide(searchResultList[position].Image, holder.binding.imgProfile,context)
    }

    override fun getItemCount(): Int {
        return searchResultList.size
    }


    class ViewHolder(var binding: LayoutRestaurantSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: Restaurants) {

            binding.searchBinding = data
            binding.executePendingBindings()


        }


//        val txtRestaurantName=itemView.findViewById<TextView>(R.id.txt_name_lrestaurant)!!
//        val txtRestaurantAddress=itemView.findViewById<TextView>(R.id.txt_address_lrestaurant)!!
//        val txtRating=itemView.findViewById<TextView>(R.id.txt_rating_lrestaurant)!!
//        val txtTotalRatingCount=itemView.findViewById<TextView>(R.id.txt_points_lrestaurant)!!
//        val imgRestaurantImage=itemView.findViewById<ImageView>(R.id.img_profile)!!
    }


    fun updateSearchList(restaurantsList: ArrayList<Restaurants>) {
        this.searchResultList = restaurantsList
        notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(img_profile: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Glide.with(img_profile.context).load(url).into(img_profile)
            }
        }
    }
}