package com.teletaleem.rmrs_customer.ui.orderdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaopiz.kprogresshud.KProgressHUD
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.OrderDetailIItemsAdapter
import com.teletaleem.rmrs_customer.data_class.checkout.MenuOrdered
import com.teletaleem.rmrs_customer.data_class.checkout.checkout_response.Data
import com.teletaleem.rmrs_customer.databinding.ActivityOrderDetailBinding
import com.teletaleem.rmrs_customer.utilities.AppGlobal


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
        getMyOrdersList(AppGlobal.readString(this,AppGlobal.customerId,"0"),orderId)
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
        mBinding.txtSubtotalAod.text=AppGlobal.mCurrency+checkoutResponse.SubTotal.toString()
        mBinding.txtSalesTaxAod.text=AppGlobal.mCurrency+checkoutResponse.SalesTax
        mBinding.txtServiceChargesAod.text=AppGlobal.mCurrency+checkoutResponse.DeliveryCharges
        mBinding.txtTotalAmountAod.text=AppGlobal.mCurrency+checkoutResponse.TotalAmount.toString()
        mBinding.edtxtSpecialInsOd.setText(checkoutResponse.Comments)
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
        progressDialog.setLabel("Please Wait")
        progressDialog.show()
        mViewModel.getOrderDetailResponse(customerId, orderId).observe(this, {
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

        })
    }



}