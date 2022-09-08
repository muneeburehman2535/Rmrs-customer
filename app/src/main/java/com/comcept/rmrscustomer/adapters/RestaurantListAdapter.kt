package com.comcept.rmrscustomer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.data_class.verifyInvoice.RestaurantList
import com.comcept.rmrscustomer.ui.home.verifyInvoice.RestaurantListListener


class RestaurantListAdapter(private val requireContext: Context, private var restaurantList: ArrayList<RestaurantList>) : RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {

companion object{

    private lateinit var restaurantListListener: RestaurantListListener

    fun setRestaurantClickListener(restaurantListListener: RestaurantListListener) {
        this.restaurantListListener = restaurantListListener
    }

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantListAdapter.ViewHolder {
        val contactView=LayoutInflater.from(parent.context).inflate(R.layout.restaurant_list_layout,parent,false)
        return ViewHolder(contactView)

//        val binding: RestaurantListLayoutBinding = DataBindingUtil.inflate(
//            LayoutInflater.from(parent.context),
//            R.layout.restaurant_list_layout,parent,false
//        )
//        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RestaurantListAdapter.ViewHolder, position: Int) {

        val item =restaurantList[position]

        holder.restaurantName.text = item.RestaurantName
        holder.restaurantAddress.text = item.Address

        holder.restaurantCardView.setOnClickListener {
            restaurantListListener.onRestaurantClickListener(position)
        }

    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val restaurantName = itemView.findViewById<TextView>(R.id.txt_restaurant_name)!!
        val restaurantAddress = itemView.findViewById<TextView>(R.id.txt_restaurant_address)!!
        val restaurantCardView = itemView.findViewById<CardView>(R.id.restaurantListCardView)!!

    }

    fun updateRestaurantList(restaurantList: ArrayList<RestaurantList>) {
        this.restaurantList = restaurantList
        notifyDataSetChanged()
    }

}

