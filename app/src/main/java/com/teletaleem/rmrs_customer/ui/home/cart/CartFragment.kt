package com.teletaleem.rmrs_customer.ui.home.cart

import android.annotation.SuppressLint
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
import com.teletaleem.rmrs_customer.data_class.cart.Cart
import com.teletaleem.rmrs_customer.databinding.CartFragmentBinding
import com.teletaleem.rmrs_customer.ui.checkout.CheckoutFragment
import com.teletaleem.rmrs_customer.ui.home.CustomerHomeActivity
import com.teletaleem.rmrs_customer.utilities.AppGlobal

class CartFragment : Fragment(),View.OnClickListener,CartItemAdapter.UpdateItemQuantityListener {
    private lateinit var mBinding:CartFragmentBinding
    private lateinit var cartList:ArrayList<Cart>
    private lateinit var cartItemAdapter:CartItemAdapter

    private var mSalesTax="7.5"
    private var mItemTotalAmount="0.00"
    private var mDiscountTotal="0.00"
    private var mTotalDiscountedAmount="0.00"
    private var mSalesTaxAmount="0.00"
    private var mServicesCharges="40.00"
    private var mTotalAmount="0.00"

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
        setCartList()
        setDealsAdapter()
        calculatePrice()
        setViews()
        setClickListeners()

    }

    private fun setCartList() {
        cartList= arrayListOf()

        val cart=Cart("Pizza BBQ","Size Regular, Wheat thin crust onion, jalapeno, Black olive","999","120","2")
        cartList.add(cart)

        val cart1=Cart("Beef Burger","Grilled Beef, Fries, Black olive","500","80","1")
        cartList.add(cart1)
    }
    /*****************************************************************************************************************************/
    //                                          Set Click Listeners
    /*****************************************************************************************************************************/
    private fun setClickListeners() {
        mBinding.btnPayToProceed.setOnClickListener(this)
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
    /*****************************************************************************************************************************/
    //                                    1:- Set Recycler View Adapter
    //                                    2:- Set Update Quantity Interface Listener
    /*****************************************************************************************************************************/
    private fun setDealsAdapter() {
        cartItemAdapter = CartItemAdapter(requireContext(),cartList)
        mBinding.rvDeliveryLocCf.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
        )
        cartItemAdapter.setUpdateItemQuantityListener(this)
        mBinding.rvDeliveryLocCf.adapter = cartItemAdapter
        //setRecyclerViewListener(mBinding.rvDeals)
    }

    override fun onUpdateItemQuantityClick(quantity: Int?, position: Int) {
        if (quantity==0)
        {
            cartList.removeAt(position)

        }
        else{
            cartList[position].quantity=quantity.toString()
        }

        cartItemAdapter.updateList(cartList)
        calculatePrice()
        setViews()
    }

    /*****************************************************************************************************************************/
    //                                    1:- Calculate Total Amounts
    //                                    2:- Set Amounts on Views
    /*****************************************************************************************************************************/


    private fun calculatePrice() {
        mItemTotalAmount="0.0"
        mDiscountTotal="0.0"
        for (index in 0 until cartList.size)
        {
            mItemTotalAmount= AppGlobal.roundTwoPlaces(mItemTotalAmount.toDouble()+(cartList[index].item_price.toDouble()*cartList[index].quantity.toDouble())).toString()
            mDiscountTotal= AppGlobal.roundTwoPlaces(mDiscountTotal.toDouble()+(cartList[index].discount.toDouble()*cartList[index].quantity.toDouble())).toString()

        }
        mTotalDiscountedAmount=AppGlobal.roundTwoPlaces(mItemTotalAmount.toDouble()-mDiscountTotal.toDouble()).toString()
        mSalesTaxAmount=AppGlobal.roundTwoPlaces(((mSalesTax.toDouble()*mTotalDiscountedAmount.toDouble())/100.00)).toString()

        mTotalAmount=AppGlobal.roundTwoPlaces(mTotalDiscountedAmount.toDouble()+mSalesTaxAmount.toDouble()+mServicesCharges.toDouble()).toString()
    }

    @SuppressLint("SetTextI18n")
    private fun setViews() {
        mBinding.txtItemTotalCf.text=AppGlobal.mCurrency+mItemTotalAmount
        mBinding.txtDiscountCf.text=AppGlobal.mCurrency+mDiscountTotal
        mBinding.txtTaxChrgCf.text=AppGlobal.mCurrency+mSalesTaxAmount
        mBinding.txtServiceChrgCf.text=AppGlobal.mCurrency+mServicesCharges
        mBinding.txtTotalPayCf.text=AppGlobal.mCurrency+mTotalAmount
    }

}