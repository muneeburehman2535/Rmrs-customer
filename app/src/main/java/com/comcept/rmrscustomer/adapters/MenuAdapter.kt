package com.comcept.rmrscustomer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.Spanned
import android.text.style.StrikethroughSpan
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
import com.comcept.rmrscustomer.data_class.restaurantdetail.Menu
import com.comcept.rmrscustomer.databinding.CardRestaurantDetailBinding
import com.comcept.rmrscustomer.utilities.AppGlobal


class MenuAdapter(val requireContext: Context, private var menuList: ArrayList<Menu>): RecyclerView.Adapter<MenuAdapter.ViewHolder>() {


    private val STRIKE_THROUGH_SPAN = StrikethroughSpan()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.card_restaurant_detail, parent, false)
//        return ViewHolder(itemView)

        val binding:CardRestaurantDetailBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.card_restaurant_detail,parent,false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        holder.txtMenuName.text=menuList[position].MenuName
//        holder.txtDescription.text=menuList[position].Description

        holder.bind(menuList[position])
        var calculatedPrice=0.0
        var itemPrice=0.0
        if (menuList[position].isVariant)
        {
            calculatedPrice=menuList[position].Variant[0].CalculatedPrice.toDouble()
            itemPrice=menuList[position].Variant[0].ItemPrice.toDouble()
        }
        else{
            calculatedPrice=menuList[position].CalculatedPrice.toDouble()
            itemPrice=menuList[position].ItemPrice.toDouble()
        }
        holder.binding.txtPriceCrd.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(calculatedPrice)
        if (calculatedPrice<itemPrice)
        {
            val oldPrice=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(itemPrice)
            holder.binding.txtOldPriceCrd.visibility=View.VISIBLE
            holder.binding.txtOldPriceCrd.setText(oldPrice, TextView.BufferType.SPANNABLE)
            val spannable = holder.binding.txtOldPriceCrd.text as Spannable
            spannable.setSpan(STRIKE_THROUGH_SPAN, 0, oldPrice.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        else{
            holder.binding.txtOldPriceCrd.visibility=View.GONE
        }

        holder.binding.imgMenuCrd.clipToOutline=true
        //AppGlobal.loadImageIntoGlide(menuList[position].Image,holder.imgMenu,requireContext)
        AppGlobal.loadImageIntoPicasso(menuList[position].Image,holder.binding.imgMenuCrd)

    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    class ViewHolder(var binding: CardRestaurantDetailBinding): RecyclerView.ViewHolder(binding.root) {
//        val txtMenuName =itemView.findViewById<TextView>(R.id.txt_menu_name_crd)!!
//        val txtDescription=itemView.findViewById<TextView>(R.id.txt_menu_crd)!!
//        val txtLatestPrice=itemView.findViewById<TextView>(R.id.txt_price_crd)!!
//        val txtOldPrice=itemView.findViewById<TextView>(R.id.txt_old_price_crd)!!
//        val imgMenu=itemView.findViewById<ImageView>(R.id.img_menu_crd)!!

        fun bind(data:Menu){

            binding.restaurantDetailBinding = data
            binding.executePendingBindings()
        }
    }

    fun updateList(menuList: ArrayList<Menu>){
        this.menuList=menuList
        notifyDataSetChanged()
    }

    companion object{
        @JvmStatic
        @BindingAdapter("loadDetailImage")
        fun loadDetailImage(img_menu_crd:ImageView,url:String?){
            if (!url.isNullOrEmpty()){
                Glide.with(img_menu_crd.context).load(url).into(img_menu_crd)
            }
        }
    }
//    @JvmStatic
//    @BindingAdapter(value = ["getDiscount", "getPrice"], requireAll = false)
//    fun menuPrice(discount:)

}