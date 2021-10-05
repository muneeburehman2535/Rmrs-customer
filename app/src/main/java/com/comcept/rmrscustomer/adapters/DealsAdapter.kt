package com.comcept.rmrscustomer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.data_class.home.restaurants.Deals
import com.comcept.rmrscustomer.databinding.LayoutDealsBinding
import com.comcept.rmrscustomer.utilities.AppGlobal

class DealsAdapter(private val requireContext: Context,private var dealsList: ArrayList<Deals>) : RecyclerView.Adapter<DealsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealsAdapter.ViewHolder {
//        val contactView=LayoutInflater.from(parent.context).inflate(R.layout.layout_deals,parent,false)
//        return ViewHolder(contactView)

        val binding:LayoutDealsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_deals,parent,false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DealsAdapter.ViewHolder, position: Int) {

//        holder.txtRestaurantName.text=dealsList[position].name
//        holder.txtRestaurantAddress.text=dealsList[position].address
//        holder.txtRating.text=dealsList[position].rating
//        holder.txtRatingCount.text="(${dealsList[position].rating_count})"
//        holder.txtDeal.text=dealsList[position].discount

        holder.bind(dealsList[position])
        holder.binding.txtPointsLdeals.text = "(${dealsList[position].rating_count})"
        AppGlobal.loadImageIntoGlide(dealsList[position].Image, holder.binding.imgProfileDeals,requireContext)

    }

    override fun getItemCount(): Int {
        return dealsList.size
    }

    class ViewHolder(var binding: LayoutDealsBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Deals){

            binding.dealsBinding = data
            binding.executePendingBindings()

        }
    }

    fun updateList(dealsList: ArrayList<Deals>){
        this.dealsList=dealsList
        notifyDataSetChanged()
    }
    companion object {
        @JvmStatic
        @BindingAdapter("loadDealsImage")
        fun loadDealsImage(img_profile_deals:ImageView,url:String?){
            if (!url.isNullOrEmpty()){
                Glide.with(img_profile_deals.context).load(url).into(img_profile_deals)
            }
        }
    }
}








//        val imgProfile=itemView.findViewById<ImageView>(R.id.img_profile_deals)!!
//        val txtDeal=itemView.findViewById<TextView>(R.id.txt_discount_deals)!!
//        val txtRestaurantName=itemView.findViewById<TextView>(R.id.txt_name_ldeals)!!
//        val txtRestaurantAddress=itemView.findViewById<TextView>(R.id.txt_address_ldeals)!!
//        val txtRatingCount=itemView.findViewById<TextView>(R.id.txt_points_ldeals)!!
//        val txtRating=itemView.findViewById<TextView>(R.id.txt_rating_ldeals)!!