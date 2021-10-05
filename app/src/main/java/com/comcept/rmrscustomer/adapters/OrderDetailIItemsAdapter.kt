package com.comcept.rmrscustomer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.data_class.checkout.MenuOrdered
import com.comcept.rmrscustomer.databinding.CardItemsOrderDetailsBinding
import com.comcept.rmrscustomer.utilities.AppGlobal

class OrderDetailIItemsAdapter(itemContext: Context,private var menuOrderedList: ArrayList<MenuOrdered>):
    RecyclerView.Adapter<OrderDetailIItemsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.card_items_order_details,parent,false)
//        return ViewHolder(itemView)

        val binding:CardItemsOrderDetailsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.card_items_order_details,parent,false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.txtQuantity.text="${menuOrderedList[position].Quantity}x"
//        holder.txtMenuName.text=menuOrderedList[position].MenuName

        holder.bind(menuOrderedList[position])
        holder.binding.txtItemPriceCiod.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(menuOrderedList[position].MenuPrice.toDouble())
    }

    override fun getItemCount(): Int {
        return menuOrderedList.size
    }

    class ViewHolder(var binding: CardItemsOrderDetailsBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(data:MenuOrdered){
            binding.itemOrderDetailBinding = data
            binding.executePendingBindings()
        }


//        val txtQuantity=itemView.findViewById<TextView>(R.id.txt_item_quantity_ciod)!!
//        val txtMenuName=itemView.findViewById<TextView>(R.id.txt_item_name_ciod)!!
//        val txtItemPrice=itemView.findViewById<TextView>(R.id.txt_item_price_ciod)!!
//        val txtDesc=itemView.findViewById<TextView>(R.id.txt_other_item_name_ciod)!!
    }

    fun updateList(menuOrderedList: ArrayList<MenuOrdered>){
        this.menuOrderedList=menuOrderedList
        notifyDataSetChanged()
    }
}