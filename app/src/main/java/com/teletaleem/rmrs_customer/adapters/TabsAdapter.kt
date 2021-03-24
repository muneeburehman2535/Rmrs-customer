package com.teletaleem.rmrs_customer.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.teletaleem.rmrs_customer.ui.restauratntdetail.menudetail.MenuDetailFragment

class TabsAdapter(manager: FragmentManager,var context: Context,var totalTabs:Int) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
{


    override fun getCount(): Int {
       return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when(position)
        {
            0->{
                MenuDetailFragment()
            }
            1->{
                MenuDetailFragment()
            }
            2->{
                MenuDetailFragment()
            }
            else -> getItem(position)

        }
    }
}