package com.teletaleem.rmrs_customer.adapters

import `in`.aabhasjindal.otptextview.ItemView
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R

class CategoriesAdapter(context: Context): RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    private val mContext=context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val contactView=LayoutInflater.from(parent.context).inflate(R.layout.layout_category,parent,false)
        return ViewHolder(contactView )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position==0)
        {
            holder.layoutParent.background=ContextCompat.getDrawable(mContext,R.drawable.curved_rectengle_layout_background)
            holder.txtCategory.setTextColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int {
        return 7
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var layoutParent:ConstraintLayout = itemView.findViewById(R.id.layout_category)
        var txtCategory:TextView=itemView.findViewById(R.id.txt_lcategory)

    }
}