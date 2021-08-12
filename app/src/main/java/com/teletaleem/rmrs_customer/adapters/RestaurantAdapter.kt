package com.teletaleem.rmrs_customer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.home.restaurants.Restaurants
import com.teletaleem.rmrs_customer.utilities.AppGlobal

class RestaurantAdapter(private val requireContext: Context, private var restaurantsList: ArrayList<Restaurants>) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    private lateinit var addToFavouriteListener:AddToFavouriteListener
    private lateinit var viewClickListener: ViewClickListener
    interface AddToFavouriteListener {
        fun onAddToFavouriteClick(
                position: Int
        ) // pass view as argument or whatever you want.
    }

    fun setAddToFavouriteListener(addToFavouriteListener: AddToFavouriteListener) {
        this.addToFavouriteListener = addToFavouriteListener
    }



    interface ViewClickListener {
        fun onViewClicked(
                position: Int
        ) // pass view as argument or whatever you want.
    }

    fun setViewClickListener(viewClickListener: ViewClickListener) {
        this.viewClickListener = viewClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val contactView=LayoutInflater.from(parent.context).inflate(R.layout.card_layout_restaurant,parent,false)
        return ViewHolder(contactView)
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.imgHeart.setImageDrawable(requireContext.getDrawable(R.drawable.ic_heart))

        if(restaurantsList[position].isFavourite)
        {
            holder.imgHeart.setImageDrawable(requireContext.getDrawable(R.drawable.ic_heart_red))
        }

        AppGlobal.loadImageIntoGlide(restaurantsList[position].Image,holder.imgRestaurant,requireContext)

        holder.txtRestaurantName.text=restaurantsList[position].RestaurantName
        holder.txtAddress.text=restaurantsList[position].Address
        holder.txtTotalRating.text=restaurantsList[position].Rating
        holder.txtTotalRatingCount.text="(${restaurantsList[position].RatingCount})"

        holder.imgHeart.setOnClickListener(View.OnClickListener {
            addToFavouriteListener.onAddToFavouriteClick(position)
        })

        holder.layoutCard.setOnClickListener(View.OnClickListener {
            viewClickListener.onViewClicked(position)
        })

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
        val imgHeart=itemView.findViewById<ImageView>(R.id.img_heart_lrestaurant)!!

        val layoutCard=itemView.findViewById<ConstraintLayout>(R.id.layout_card_restaurants)!!
    }

    fun updateRestaurantsList(restaurantsList: ArrayList<Restaurants>){
        this.restaurantsList=restaurantsList
        notifyDataSetChanged()
    }


}