package com.teletaleem.rmrs_customer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.restaurantdetail.Variant
import com.teletaleem.rmrs_customer.databinding.LayoutVariantCardBinding
import com.teletaleem.rmrs_customer.utilities.AppGlobal

class VariantAdapter(val context: Context,var variantList:ArrayList<Variant>): RecyclerView.Adapter<VariantAdapter.ViewHolder>() {


    private lateinit var menuSelectionListener: MenuSelectionListener
    interface MenuSelectionListener {
        fun onMenuSelectionClick(
            position: Int
        ) // pass view as argument or whatever you want.
    }

    fun setUpdateItemQuantityListener(menuSelectionListener: MenuSelectionListener) {
        this.menuSelectionListener = menuSelectionListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.layout_variant_card,parent,false)
//        return ViewHolder(itemView)

        val binding:LayoutVariantCardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_variant_card,parent,false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val variant=variantList[position]

        holder.bind(variantList[position])
        holder.binding.txtVariantPriceLvc.text="+ ${AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(variant.CalculatedPrice.toDouble())}"
        holder.binding.rbVariantLvc.isChecked = variant.isChecked
        holder.binding.layoutVatiantLvc.setOnClickListener(View.OnClickListener {
            menuSelectionListener.onMenuSelectionClick(position)
        })
    }

    override fun getItemCount(): Int {
        return variantList.size
    }

    class ViewHolder(var binding: LayoutVariantCardBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data:Variant){
            binding.variantBinding = data
            binding.executePendingBindings()
        }


//        val radioButtonVariant=itemView.findViewById<RadioButton>(R.id.rb_variant_lvc)!!
//        val txtItemName=itemView.findViewById<TextView>(R.id.txt_variant_name_lvc)!!
//        val txtItemPrice=itemView.findViewById<TextView>(R.id.txt_variant_price_lvc)!!
//        val layoutParent=itemView.findViewById<ConstraintLayout>(R.id.layout_vatiant_lvc)!!
    }

    fun updateList(variantList: ArrayList<Variant>){
        this.variantList=variantList
        notifyDataSetChanged()
    }

}