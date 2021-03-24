package com.teletaleem.rmrs_customer.ui.restauratntdetail

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.TabsAdapter
import com.teletaleem.rmrs_customer.databinding.RestaurantDetailFragmentBinding
import com.teletaleem.rmrs_customer.utilities.BlurBuilder


class RestaurantDetailFragment : Fragment() {
    private lateinit var mBinding:RestaurantDetailFragmentBinding

    companion object {
        fun newInstance() = RestaurantDetailFragment()
    }

    private lateinit var viewModel: RestaurantDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding=DataBindingUtil.inflate(
            inflater,
            R.layout.restaurant_detail_fragment,
            container,
            false
        )
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RestaurantDetailViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBlurBackgroundImage()
        setTabLayout()
    }

    private fun setTabLayout() {
        mBinding.layoutTabRdf.addTab(mBinding.layoutTabRdf.newTab().setText("Home"));
        mBinding.layoutTabRdf.addTab(mBinding.layoutTabRdf.newTab().setText("Sport"));
        mBinding.layoutTabRdf.addTab(mBinding.layoutTabRdf.newTab().setText("Movie"));
        mBinding.layoutTabRdf.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = TabsAdapter( childFragmentManager,requireContext(), mBinding.layoutTabRdf.tabCount)
        mBinding.viewpagerRdf.adapter = adapter
        mBinding.viewpagerRdf.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mBinding.layoutTabRdf))
        mBinding.layoutTabRdf.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mBinding.viewpagerRdf.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setBlurBackgroundImage() {
        val originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.background)
        val blurredBitmap = BlurBuilder.blur(context, originalBitmap)
        mBinding.layoutBackgroungFrd.background = BitmapDrawable(resources, blurredBitmap)

    }

}