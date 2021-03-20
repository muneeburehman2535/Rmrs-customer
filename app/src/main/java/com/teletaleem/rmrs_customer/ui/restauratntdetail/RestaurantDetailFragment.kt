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
import com.teletaleem.rmrs_customer.R
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
    }

    private fun setBlurBackgroundImage() {
        val originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.background)
        val blurredBitmap = BlurBuilder.blur(context, originalBitmap)
        mBinding.layoutBackgroungFrd.background = BitmapDrawable(resources, blurredBitmap)

    }

}