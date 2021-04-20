package com.teletaleem.rmrs_customer.ui.review.restaurantreviews

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.databinding.ReviewsListFragmentBinding
import com.teletaleem.rmrs_customer.ui.review.ReviewViewModel

class ReviewsListFragment : Fragment() {

    companion object {
        fun newInstance() = ReviewsListFragment()
    }

    private lateinit var viewModel: ReviewViewModel
    private lateinit var mBinding:ReviewsListFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding=DataBindingUtil.inflate(inflater,R.layout.reviews_list_fragment,container,false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)

    }

}