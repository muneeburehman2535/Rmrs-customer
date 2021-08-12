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
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.home.category.Categories
import com.teletaleem.rmrs_customer.databinding.LayoutCategoryBinding

class CategoriesAdapter(private val context: Context,private var categoriesList: ArrayList<Categories>): RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    private val mContext=context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: LayoutCategoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_category,parent,false
        )
        return ViewHolder(binding)

//        val contactView=LayoutInflater.from(parent.context).inflate(R.layout.layout_category,parent,false)
//        return ViewHolder(contactView )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(categoriesList[position])
        holder.binding.layoutCategory.background = ContextCompat.getDrawable(mContext,R.drawable.curved_rectengle_layout_background_light)
        holder.binding.txtLcategory.setTextColor(R.color.colorAccent)
        //holder.binding.layout_category.background=ContextCompat.getDrawable(mContext,R.drawable.curved_rectengle_layout_background_light)
        //holder.binding.txt_lcategory.setTextColor(R.color.colorAccent)
        if (categoriesList[position].isClicked)
        {
            holder.binding.layoutCategory.background=ContextCompat.getDrawable(mContext,R.drawable.curved_rectengle_layout_background)
            holder.binding.txtLcategory.setTextColor(Color.WHITE)
        }
        else{
            holder.binding.txtLcategory.setTextColor(context.getColor(R.color.colorAccent))
        }
        holder.binding.txtLcategory.text=categoriesList[position].CategoryName

    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    class ViewHolder(var binding: LayoutCategoryBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Categories){

            binding.categoryBinding = data
            binding.executePendingBindings()

        }

    }

    fun updateCategoryList(categoriesList: ArrayList<Categories>){
        this.categoriesList=categoriesList
        notifyDataSetChanged()
    }
}