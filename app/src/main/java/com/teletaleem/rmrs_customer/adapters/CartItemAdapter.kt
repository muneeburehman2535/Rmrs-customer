package com.teletaleem.rmrs_customer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.cart.Cart
import com.teletaleem.rmrs_customer.utilities.AppGlobal

class CartItemAdapter(requireContext: Context, private var cartList:ArrayList<Cart>) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    private lateinit var updateItemQuantityListener:UpdateItemQuantityListener
    interface UpdateItemQuantityListener {
        fun onUpdateItemQuantityClick(
            quantity: Int?,
            position: Int
        ) // pass view as argument or whatever you want.
    }

    fun setUpdateItemQuantityListener(updateItemQuantityListener: UpdateItemQuantityListener) {
        this.updateItemQuantityListener = updateItemQuantityListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val itemView=LayoutInflater.from(parent.context).inflate(
           R.layout.layout_card_item_card,
           parent,
           false
       )
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtItemName.text=cartList[position].item_name
        holder.txtItemDesc.text=cartList[position].item_desc
        holder.txtItemPrice.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(cartList[position].item_price.toDouble())
        holder.txtItemQuantity.text=cartList[position].quantity

        holder.txtDecreaseQuantity.setOnClickListener(View.OnClickListener {
            updateItemQuantityListener.onUpdateItemQuantityClick(cartList[position].quantity.toInt()-1,position)
        })

        holder.txtIncreaseQuantity.setOnClickListener(View.OnClickListener {
            updateItemQuantityListener.onUpdateItemQuantityClick(cartList[position].quantity.toInt()+1,position)

        })
    }

    override fun getItemCount(): Int {
       return cartList.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val txtItemName=itemView.findViewById<TextView>(R.id.txt_item_name_lcic)
        val txtItemDesc=itemView.findViewById<TextView>(R.id.txt_item_desc_lcic)
        val txtItemPrice=itemView.findViewById<TextView>(R.id.txt_item_price_lcic)
        val txtItemQuantity=itemView.findViewById<TextView>(R.id.txt_quantity_lcic)
        val txtIncreaseQuantity=itemView.findViewById<TextView>(R.id.txt_plus_lcic)
        val txtDecreaseQuantity=itemView.findViewById<TextView>(R.id.txt_minus_lcic)
    }

    fun updateList(cartList:ArrayList<Cart>){
        this.cartList=cartList
        notifyDataSetChanged()
    }


}