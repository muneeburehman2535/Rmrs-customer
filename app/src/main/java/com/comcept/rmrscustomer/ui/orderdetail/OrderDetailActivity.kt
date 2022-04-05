package com.comcept.rmrscustomer.ui.orderdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaopiz.kprogresshud.KProgressHUD
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.adapters.OrderDetailIItemsAdapter
import com.comcept.rmrscustomer.data_class.checkout.MenuOrdered
import com.comcept.rmrscustomer.data_class.checkout.checkout_response.Data
import com.comcept.rmrscustomer.databinding.ActivityOrderDetailBinding
import com.comcept.rmrscustomer.repository.Response
import com.comcept.rmrscustomer.utilities.AppGlobal


class OrderDetailActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mBinding:ActivityOrderDetailBinding
    private lateinit var mViewModel: OrderDetailViewModel
    private lateinit var orderDetailIItemsAdapter:OrderDetailIItemsAdapter
    private lateinit var progressDialog: KProgressHUD
    private lateinit var orderId:String
    private lateinit var menuOrderedList:ArrayList<MenuOrdered>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=DataBindingUtil.setContentView(this, R.layout.activity_order_detail)
        mViewModel=ViewModelProvider(this).get(OrderDetailViewModel::class.java)
        mBinding.orderDetailViewModel=mViewModel
        getOrderId()
        progressDialog=AppGlobal.setProgressDialog(this)
        setRestaurantAdapter()
        setClickListeners()
        if (AppGlobal.isInternetAvailable(this)){
            getMyOrdersList(AppGlobal.readString(this,AppGlobal.customerId,"0"),orderId)
        }
        else{
            AppGlobal.snackBar(mBinding.layputParentAod,getString(R.string.err_no_internet),AppGlobal.SHORT)
        }


    }

    private fun getOrderId() {
        orderId= intent.getStringExtra("order_id").toString()
    }

    private fun setClickListeners() {
        mBinding.imgBackOrderDetail.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    private fun setViews(checkoutResponse: Data) {
        mBinding.txtTitleRestaurantNameDetail.text=checkoutResponse.RestaurantName
        mBinding.txtOrderNumber.text="#${checkoutResponse.OrderID}"
        mBinding.txtOrderFrom.text=checkoutResponse.RestaurantName
        mBinding.txtSubtotalAod.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(checkoutResponse.SubTotal.toString().toDouble())
        mBinding.txtSalesTaxAod.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(checkoutResponse.SalesTax.toDouble())
        mBinding.txtServiceChargesAod.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(checkoutResponse.DeliveryCharges.toDouble())
        mBinding.txtTotalAmountAod.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(checkoutResponse.TotalAmount.toDouble())
        mBinding.edtxtSpecialInsOd.setText(checkoutResponse.Comments)
        mBinding.txtOrderDelivery.text = checkoutResponse.CustomerAddress
        //AppGlobal.loadImageIntoGlide(mBinding.imgBackgroundOrderDetail,checkoutResponse.)
    }

    private fun setRestaurantAdapter() {
        menuOrderedList= arrayListOf()
        orderDetailIItemsAdapter = OrderDetailIItemsAdapter(this,menuOrderedList)
        mBinding.rvItemsOrderDetail.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        )
        mBinding.rvItemsOrderDetail.adapter = orderDetailIItemsAdapter
        //setRecyclerViewListener(mBinding.rvItemsOrderDetail)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.img_back_order_detail -> {
                this.finish()
            }
        }
    }


    /*
  * Get Restaurants Data API Method
  * */
    private fun getMyOrdersList(customerId: String, orderId: String){

        mViewModel.getOrderDetailResponse(customerId, orderId).observe(this, {

           when(it){

               is Response.Loading ->{
                   progressDialog.setLabel("Please Wait")
                   progressDialog.show()
               }

               is Response.Success ->{

                   it.data?.let {
                       progressDialog.dismiss()
                       if (it!=null){
                           if (it.Message == "Success") {
                               menuOrderedList=it.data.MenuOrdered
                               orderDetailIItemsAdapter.updateList(menuOrderedList)

                               setViews(it.data)

                           } else {
                               AppGlobal.showDialog(
                                   getString(R.string.title_alert),
                                   it.data.description,
                                   this
                               )
                           }
                       }

                   }
               }

               is Response.Error ->{
                   AppGlobal.showDialog(getString(R.string.title_alert), it.message.toString(),this)
                   if (progressDialog.isShowing) {
                       progressDialog.dismiss()

                   }
               }
           }

        })
    }



}