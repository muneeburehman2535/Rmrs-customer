package com.teletaleem.rmrs_customer.adapters

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
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.restaurantdetail.Menu
import com.teletaleem.rmrs_customer.utilities.AppGlobal


class MenuAdapter(val requireContext: Context, private var menuList: ArrayList<Menu>): RecyclerView.Adapter<MenuAdapter.ViewHolder>() {


    private val STRIKE_THROUGH_SPAN = StrikethroughSpan()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.card_restaurant_detail, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txtMenuName.text=menuList[position].MenuName
        holder.txtDescription.text=menuList[position].Description
        holder.txtLatestPrice.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(menuList[position].MenuPrice.toDouble())
        if (menuList[position].MenuPrice!=menuList[position].OriginalPrice)
        {
            val oldPrice=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(menuList[position].OriginalPrice.toDouble())
            holder.txtOldPrice.visibility=View.VISIBLE
            holder.txtOldPrice.setText(oldPrice, TextView.BufferType.SPANNABLE)
            val spannable = holder.txtOldPrice.text as Spannable
            spannable.setSpan(STRIKE_THROUGH_SPAN, 0, oldPrice.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        else{
            holder.txtOldPrice.visibility=View.GONE
        }

        //AppGlobal.loadImageIntoGlide(menuList[position].M,holder.imgRestaurant,requireContext)

    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtMenuName =itemView.findViewById<TextView>(R.id.txt_menu_name_crd)!!
        val txtDescription=itemView.findViewById<TextView>(R.id.txt_menu_crd)!!
        val txtLatestPrice=itemView.findViewById<TextView>(R.id.txt_price_crd)!!
        val txtOldPrice=itemView.findViewById<TextView>(R.id.txt_old_price_crd)!!
        val imgMenu=itemView.findViewById<ImageView>(R.id.img_menu_crd)!!
    }

    fun updateList(menuList: ArrayList<Menu>){
        this.menuList=menuList
        notifyDataSetChanged()
    }
}