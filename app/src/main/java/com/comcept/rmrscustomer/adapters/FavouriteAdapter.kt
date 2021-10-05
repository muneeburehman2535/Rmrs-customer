package com.comcept.rmrscustomer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.db.dataclass.Favourite
import com.comcept.rmrscustomer.utilities.AppGlobal

class FavouriteAdapter(private val requireActivity:Context,private var favouriteList:ArrayList<Favourite>): RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val contactView= LayoutInflater.from(parent.context).inflate(R.layout.layout_favourite,parent,false)
        return ViewHolder(contactView)
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        AppGlobal.loadImageIntoGlide(favouriteList[position].image,holder.imgRestaurant,requireActivity)

        holder.txtRestaurantName.text=favouriteList[position].restaurant_name
        holder.txtAddress.text=favouriteList[position].address
        holder.txtTotalRating.text=favouriteList[position].rating
        holder.txtTotalRatingCount.text="(${favouriteList[position].rating_count})"

    }

    override fun getItemCount(): Int {
       return favouriteList.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val imgRestaurant= itemView.findViewById<ImageView>(R.id.img_profile_lf)!!
        val txtRestaurantName= itemView.findViewById<TextView>(R.id.txt_name_lf)!!
        val txtAddress= itemView.findViewById<TextView>(R.id.txt_address_lf)!!
        val txtTotalRatingCount= itemView.findViewById<TextView>(R.id.txt_points_lf)!!
        val txtTotalRating= itemView.findViewById<TextView>(R.id.txt_rating_lf)!!

    }

    fun updateList(favouriteList: ArrayList<Favourite>)
    {
        this.favouriteList=favouriteList
        notifyDataSetChanged()
    }
}