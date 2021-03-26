package com.teletaleem.rmrs_customer.ui.home.cart

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.CartItemAdapter
import com.teletaleem.rmrs_customer.databinding.CartFragmentBinding
import com.teletaleem.rmrs_customer.ui.checkout.CheckoutFragment
import com.teletaleem.rmrs_customer.ui.home.CustomerHomeActivity

class CartFragment : Fragment(),View.OnClickListener {
    private lateinit var mBinding:CartFragmentBinding

    companion object {
        fun newInstance() = CartFragment()
    }

    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding=DataBindingUtil.inflate(inflater,R.layout.cart_fragment,container,false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        mBinding.cartViewModel=viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDealsAdapter()
        setClickListeners()
    }

    private fun setClickListeners() {
        mBinding.btnPayToProceed.setOnClickListener(this)
    }


    private fun setDealsAdapter() {
        val dealsAdapter = CartItemAdapter(requireContext())
        mBinding.rvDeliveryLocCf.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
        )
        mBinding.rvDeliveryLocCf.adapter = dealsAdapter
        //setRecyclerViewListener(mBinding.rvDeals)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btn_pay_to_proceed->
            {
                (context as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_checkout))
                (context as CustomerHomeActivity?)?.loadNewFragment(
                    CheckoutFragment(),
                    "checkout"
                )
            }
        }
    }

}