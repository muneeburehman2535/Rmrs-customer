package com.teletaleem.rmrs_customer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.home.category.Categories

class CategoriesAdapter(private val context: Context,private var categoriesList: ArrayList<Categories>): RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    private val mContext=context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val contactView=LayoutInflater.from(parent.context).inflate(R.layout.layout_category,parent,false)
        return ViewHolder(contactView )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.layoutParent.background=ContextCompat.getDrawable(mContext,R.drawable.curved_rectengle_layout_background_light)
        holder.txtCategory.setTextColor(R.color.colorAccent)
        if (categoriesList[position].isClicked)
        {
            holder.layoutParent.background=ContextCompat.getDrawable(mContext,R.drawable.curved_rectengle_layout_background)
            holder.txtCategory.setTextColor(Color.WHITE)
        }
        else{
            holder.txtCategory.setTextColor(context.getColor(R.color.colorAccent))
        }
        holder.txtCategory.text=categoriesList[position].CategoryName

    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var layoutParent:ConstraintLayout = itemView.findViewById(R.id.layout_category)
        var txtCategory:TextView=itemView.findViewById(R.id.txt_lcategory)

    }

    fun updateCategoryList(categoriesList: ArrayList<Categories>){
        this.categoriesList=categoriesList
        notifyDataSetChanged()
    }
}