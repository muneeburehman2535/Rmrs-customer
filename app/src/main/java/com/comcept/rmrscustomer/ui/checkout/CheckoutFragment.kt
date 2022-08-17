package com.comcept.rmrscustomer.ui.checkout

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.kaopiz.kprogresshud.KProgressHUD
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.data_class.checkout.Checkout
import com.comcept.rmrscustomer.databinding.CheckoutFragmentBinding
import com.comcept.rmrscustomer.db.CustomerDatabase
import com.comcept.rmrscustomer.repository.Response
import com.comcept.rmrscustomer.ui.home.CustomerHomeActivity
import com.comcept.rmrscustomer.ui.home.HomeFragment
import com.comcept.rmrscustomer.ui.review.ReviewFragment
import com.comcept.rmrscustomer.utilities.AppGlobal
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class  CheckoutFragment : Fragment(),View.OnClickListener {
    private lateinit var mBinding:CheckoutFragmentBinding
    private lateinit var progressDialog: KProgressHUD
    private lateinit var orderCheckout:Checkout
    private lateinit var  databaseCreator: CustomerDatabase
    private var storeId=15597

    companion object {
        fun newInstance() = CheckoutFragment()
    }

    val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

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

            Timber.d("Checkout API: ${Gson().toJson(it)}")

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
                {mBinding.rbCheckoutEasypiasaFc.visibility = View.GONE
                    orderCheckout.PaymentMethod="COD"
                }
                if (mBinding.rbCheckoutEasypiasaFc.isChecked){
                    mBinding.rbCheckoutFc.visibility = View.GONE
                    orderCheckout.PaymentMethod="EP"
                }
                orderCheckout.OrderType="Delivery"
                orderCheckout.Comments=mBinding.edtxtSpecialInsCf.text.toString()
                orderCheckout.CustomerAddress=mBinding.txtDeliveryLocCf.text.toString()
               checkoutOrder()
            }
        }
    }


//    val emailaddres = AppGlobal.customerEmail
//
//    private fun easyPiasa(){
//
//        var sampleString = "amount=${orderCheckout.TotalAmount}&emailAddress=${emailaddres}&expiryDate=${""}&merchantPaymentMethod=MA_PAYMENT_METHOD&mobileNum=${orderCheckout.MobileNumber}&orderRefNum=${orderCheckout.}"
//    }




    /*
  * Get Restaurants Data API Method
   * */
    private fun checkoutOrder(){


        uiScope.launch {

            viewModel.getCheckoutResponse(orderCheckout).observe(requireActivity()) {

                when (it) {


                    is Response.Loading -> {
                        progressDialog.setLabel("Please Wait")
                        progressDialog.show()
                    }

                    is Response.Success -> {

                        it.data?.let {
                            progressDialog.dismiss()
                            if (it != null && it.Message == "Success") {

                                Timber.d("Checkout API: ${Gson().toJson(it)}")

                                emptyCartRecord()
                                (activity as CustomerHomeActivity?)?.changeToolbarName(
                                    getString(R.string.title_review),
                                    isProfileMenuVisible = false,
                                    locationVisibility = false,
                                    isMenuVisibility = false
                                )
                                (activity as CustomerHomeActivity?)?.loadNewFragment(
                                    HomeFragment(),
                                    "home"
                                )

                            } else {
                                AppGlobal.showDialog(
                                    getString(R.string.title_alert),
                                    it.Message,
                                    requireActivity()
                                )
                            }

                        }

                    }
                    is Response.Error -> {
                        AppGlobal.showDialog(
                            getString(R.string.title_alert),
                            it.message.toString(),
                            requireActivity()
                        )
                        if (progressDialog.isShowing) {

                            progressDialog.dismiss()

                        }

                    }
                }
            }

        }


    }

    private fun emptyCartRecord() {
        viewModel.emptyCartRecord()
    }

}