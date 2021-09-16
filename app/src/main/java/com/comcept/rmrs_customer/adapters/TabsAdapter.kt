package com.comcept.rmrs_customer.adapters

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.comcept.rmrs_customer.data_class.restaurantdetail.Menu
import com.comcept.rmrs_customer.ui.restauratntdetail.menudetail.MenuDetailFragment


class TabsAdapter(manager: FragmentManager, var context: Activity, var totalTabs: Int, private val menuList: ArrayList<Menu>,private val tabList: ArrayList<String>,private var isNem:Boolean) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
{

    var fragment: Fragment? = null
    private lateinit var viewClickListener: ViewClickListener
    private var isClicked= false
    interface ViewClickListener {
        fun onViewClicked(
                menuList: ArrayList<Menu>
        ) // pass view as argument or whatever you want.
    }

    fun setViewClickListener(viewClickListener: ViewClickListener) {
        this.viewClickListener = viewClickListener
    }


    override fun getCount(): Int {
       return totalTabs
    }

    override fun getItem(position: Int): Fragment {

        val updatedMenuList= arrayListOf<Menu>()
        for (index in 0 until menuList.size)
        {
            if (isNem){
                if (menuList[index].MenuCategory==tabList[0]&&position==0)
                {
                    updatedMenuList.add(menuList[index])
                }
            }
            else{
                if (menuList[index].MenuCategory==tabList[position-1])
                {
                    updatedMenuList.add(menuList[index])
                }
            }

        }
        if (isNem)
        {
            if (!isClicked){
                isClicked=true
                viewClickListener.onViewClicked(updatedMenuList)
            }
        }
        else{
            viewClickListener.onViewClicked(updatedMenuList)
        }



        //(context as CustomerHomeActivity).mModel.updateMenuList(updatedMenuList)
        return MenuDetailFragment.getInstance(position,updatedMenuList)

    }

    fun updateNewKey(isNew:Boolean){
        isNem=isNew
    }
}