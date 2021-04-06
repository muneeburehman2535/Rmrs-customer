package com.teletaleem.rmrs_customer.ui.checkout

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.databinding.CheckoutFragmentBinding
import com.teletaleem.rmrs_customer.ui.home.CustomerHomeActivity
import com.teletaleem.rmrs_customer.ui.review.ReviewFragment

class CheckoutFragment : Fragment(),View.OnClickListener {
    private lateinit var mBinding:CheckoutFragmentBinding

    companion object {
        fun newInstance() = CheckoutFragment()
    }

    private lateinit var viewModel: CheckoutViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        mBinding=DataBindingUtil.inflate(inflater,R.layout.checkout_fragment,container,false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CheckoutViewModel::class.java)
        mBinding.checkoutViewModel=viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() {
        mBinding.btnCheckoutFc.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btn_checkout_fc->
            {
                (context as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_review))
                (context as CustomerHomeActivity?)?.loadNewFragment(
                    ReviewFragment(),
                    "review"
                )
            }
        }
    }

}