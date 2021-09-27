package com.comcept.rmrs_customer.ui.checkout

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.kaopiz.kprogresshud.KProgressHUD
import com.comcept.rmrs_customer.R
import com.comcept.rmrs_customer.data_class.checkout.Checkout
import com.comcept.rmrs_customer.databinding.CheckoutFragmentBinding
import com.comcept.rmrs_customer.db.CustomerDatabase
import com.comcept.rmrs_customer.ui.home.CustomerHomeActivity
import com.comcept.rmrs_customer.ui.review.ReviewFragment
import com.comcept.rmrs_customer.utilities.AppGlobal
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class  CheckoutFragment : Fragment(),View.OnClickListener {
    private lateinit var mBinding:CheckoutFragmentBinding
    private lateinit var progressDialog: KProgressHUD
    private lateinit var orderCheckout:Checkout
    private lateinit var  databaseCreator: CustomerDatabase

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
        databaseCreator= CustomerDatabase.getInstance(requireActivity())
        progressDialog=AppGlobal.setProgressDialog(requireActivity())
        setClickListeners()
        getOrderData()
        mBinding.txtDeliveryLocCf.text=AppGlobal.readString(requireActivity(),AppGlobal.customerAddress,"")
    }

    private fun getOrderData()
    {
        (activity as CustomerHomeActivity?)?.mModel?.checkout?.observe(viewLifecycleOwner, Observer {
            this.orderCheckout=it
        })
    }
    override fun onResume() {
        super.onResume()
        (activity as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_checkout), isProfileMenuVisible = false, locationVisibility = true,isMenuVisibility = false)
    }


    private fun setClickListeners() {
        mBinding.btnCheckoutFc.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btn_checkout_fc->
            {
                if (mBinding.rbCheckoutFc.isChecked)
                {
                    orderCheckout.PaymentMethod="COD"
                }
                orderCheckout.OrderType="Delivery"
                orderCheckout.Comments=mBinding.edtxtSpecialInsCf.text.toString()
                orderCheckout.CustomerAddress=mBinding.txtDeliveryLocCf.text.toString()
               checkoutOrder()
            }
        }
    }

    /*
  * Get Restaurants Data API Method
  * */
    private fun checkoutOrder(){
        progressDialog.setLabel("Please Wait")
        progressDialog.show()
        viewModel.getCheckoutResponse(orderCheckout).observe(requireActivity(), {
            progressDialog.dismiss()
            if (it.Message == "Success") {
                emptyCartRecord()
                (activity as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_review), isProfileMenuVisible = false, locationVisibility = false,isMenuVisibility = false)
                (activity as CustomerHomeActivity?)?.loadNewFragment(
                        ReviewFragment(),
                        "review"
                )

            } else {
                AppGlobal.showDialog(getString(R.string.title_alert), it.data.description, requireActivity())
            }
        })
    }

    private fun emptyCartRecord() {
        viewModel.emptyCartRecord()
    }

}