package com.teletaleem.rmrs_customer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.checkout.MenuOrdered
import com.teletaleem.rmrs_customer.utilities.AppGlobal

class OrderDetailIItemsAdapter(itemContext: Context,private var menuOrderedList: ArrayList<MenuOrdered>):
    RecyclerView.Adapter<OrderDetailIItemsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.card_items_order_details,parent,false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtQuantity.text="${menuOrderedList[position].Quantity}x"
        holder.txtMenuName.text=menuOrderedList[position].MenuName
        holder.txtItemPrice.text=AppGlobal.mCurrency+menuOrderedList[position].MenuPrice
    }

    override fun getItemCount(): Int {
        return menuOrderedList.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val txtQuantity=itemView.findViewById<TextView>(R.id.txt_item_quantity_ciod)!!
        val txtMenuName=itemView.findViewById<TextView>(R.id.txt_item_name_ciod)!!
        val txtItemPrice=itemView.findViewById<TextView>(R.id.txt_item_price_ciod)!!
        val txtDesc=itemView.findViewById<TextView>(R.id.txt_other_item_name_ciod)!!
    }

    fun updateList(menuOrderedList: ArrayList<MenuOrdered>){
        this.menuOrderedList=menuOrderedList
        notifyDataSetChanged()
    }
}