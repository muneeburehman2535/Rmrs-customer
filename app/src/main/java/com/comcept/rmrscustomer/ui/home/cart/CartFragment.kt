package com.comcept.rmrscustomer.ui.home.cart

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.adapters.CartItemAdapter
import com.comcept.rmrscustomer.data_class.cart.Cart
import com.comcept.rmrscustomer.data_class.checkout.Checkout
import com.comcept.rmrscustomer.data_class.checkout.Delivery
import com.comcept.rmrscustomer.data_class.checkout.MenuOrdered
import com.comcept.rmrscustomer.data_class.restaurantdetail.RestaurantDetailResponse
import com.comcept.rmrscustomer.data_class.restaurantdetail.Variant
import com.comcept.rmrscustomer.databinding.CartFragmentBinding
import com.comcept.rmrscustomer.db.CustomerDatabase
import com.comcept.rmrscustomer.ui.checkout.CheckoutFragment
import com.comcept.rmrscustomer.ui.home.CustomerHomeActivity
import com.comcept.rmrscustomer.utilities.AppGlobal
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CartFragment : Fragment(),View.OnClickListener,CartItemAdapter.UpdateItemQuantityListener {
    private lateinit var mBinding:CartFragmentBinding
    private lateinit var cartList:ArrayList<Cart>
    private lateinit var cartItemAdapter:CartItemAdapter

    private var mSalesTax="0"
    private var mItemTotalAmount="0"
    private var mDiscountTotal="0"
    private var mTotalDiscountedAmount="0"
    private var mSalesTaxAmount="0"
    private var mServicesCharges="0"
    private var mTotalAmount="0"
    private var restaurantId="0"
    private var restaurantName=""
    private lateinit var ownerId:String

    private lateinit var  databaseCreator: CustomerDatabase

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
        databaseCreator= CustomerDatabase.getInstance(requireActivity())
        mSalesTax=AppGlobal.readString(requireActivity(),AppGlobal.salesTax,"0")
        mServicesCharges=AppGlobal.readString(requireActivity(),AppGlobal.serviceCharges,"0")
        setCartList()
        setDealsAdapter()
        getRestaurantId()
        getOwnerId()
//        calculatePrice()
//        setViews()
        setClickListeners()





    }

    private fun setCartList() {
        cartList= arrayListOf()

//        val cart=Cart("1","abc","Pizza BBQ","1","Size Regular, Wheat thin crust onion, jalapeno, Black olive","999","120","2","","test")
//        cartList.add(cart)
//
//        val cart1=Cart("1","abcs","Beef Burger","2","Grilled Beef, Fries, Black olive","500","80","1","","test")
//        cartList.add(cart1)
    }

    private fun getRestaurantId(){
        restaurantId=AppGlobal.readString(requireActivity(),AppGlobal.restaurantId,"")
        getCartRecord()
    }

    private fun getOwnerId(){
        ownerId=AppGlobal.readString(requireActivity(),AppGlobal.ownerId,"0")
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
                bindCheckoutData()
            }
        }
    }
    /*****************************************************************************************************************************/
    //                                    1:- Set Recycler View Adapter
    //                                    2:- Set Update Quantity Interface Listener
    /*****************************************************************************************************************************/
    private fun setDealsAdapter() {
        cartItemAdapter = CartItemAdapter(requireActivity(),cartList)
        mBinding.rvDeliveryLocCf.layoutManager = LinearLayoutManager(
                activity,
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
            deleteItemRow(cartList[position])
        }
        else{
            cartList[position].quantity=quantity.toString()
            cartList[position].quantity=quantity.toString()
            updateCart(cartList[position])
        }

    }

    /*****************************************************************************************************************************/
    //                                    1:- Calculate Total Amounts
    //                                    2:- Set Amounts on Views
    //                                    3:- Bind Data for checkout
    //                                    4:- Redirect to checkout
    /*****************************************************************************************************************************/


    private fun calculatePrice() {
        mItemTotalAmount="0"
        mDiscountTotal="0"
        for (index in 0 until cartList.size)
        {

            mItemTotalAmount= (mItemTotalAmount.toDouble()+(cartList[index].original_price.toDouble()*cartList[index].quantity.toDouble())).toString()
            mDiscountTotal= (mDiscountTotal.toDouble()+(cartList[index].item_price.toDouble()*cartList[index].quantity.toDouble())).toString()

        }
        mTotalDiscountedAmount = if (mItemTotalAmount.toDouble()>mDiscountTotal.toDouble())
        {
           AppGlobal.roundTwoPlaces(mDiscountTotal.toDouble()).toString()
        }
        else{
            mItemTotalAmount
        }

        mSalesTaxAmount=(((mSalesTax.toDouble()*mTotalDiscountedAmount.toDouble())/100)).toString()

        mTotalAmount=(mTotalDiscountedAmount.toDouble()+mSalesTaxAmount.toDouble()+mServicesCharges.toDouble()).toString()
    }

    @SuppressLint("SetTextI18n")
    private fun setViews() {
        mBinding.txtDeliveryLocCartf.text=AppGlobal.readString(requireActivity(),AppGlobal.customerAddress,"")
        mBinding.txtItemTotalCf.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(mItemTotalAmount.toDouble())
        mBinding.txtDiscountCf.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(mDiscountTotal.toDouble())
        mBinding.txtTaxChrgCf.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(mSalesTaxAmount.toDouble())
        mBinding.txtServiceChrgCf.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(mServicesCharges.toDouble())
        mBinding.txtTotalPayCf.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(mTotalAmount.toDouble())
        if (cartList.size>0){
            mBinding.txtRestaurantNameLcic.text=cartList[0].restaurant_name
            mBinding.txtRestaurantLocLcic.text=cartList[0].restaurant_address
            if (activity!=null){
                AppGlobal.loadImageIntoGlide(cartList[0].restaurant_image,mBinding.imgRestaurantLcic,requireActivity())
            }

        }
        mBinding.txtTitleTaxChrgCf.setText("${getString(R.string.title_tax)} ($mSalesTax %)")

    }

    private fun bindCheckoutData(){

        val menuOrderedList= arrayListOf<MenuOrdered>()
        for (index in 0 until cartList.size)
        {
            val variant= Gson().fromJson(cartList[index].variant, Variant::class.java)
            if (!cartList[index].is_variant.toBoolean())
            {
                variant.ItemName="Standard"
                variant.CalculatedPrice=(0.0).toFloat()
                variant.ItemPrice=(0.0).toFloat()
                variant.Quantity=0
                variant.DiscountPrice=0
            }

            val isDeal= cartList[index].is_deal==1

            val menuOrdered=MenuOrdered(cartList[index].menu_id,cartList[index].item_name,cartList[index].item_price
                ,cartList[index].quantity,cartList[index].description, variant,cartList[index].is_variant.toBoolean(),isDeal,false)
            menuOrdered.Variant.Quantity=cartList[index].quantity.toInt()
            menuOrderedList.add(menuOrdered)

        }
        val mCheckout=Checkout(cartList[0].restaurant_id
            ,cartList[0].restaurant_name
            ,true
            ,AppGlobal.readString(requireActivity(),AppGlobal.customerId,"")
            ,AppGlobal.readString(requireActivity(),AppGlobal.customerName,"")
            ,mTotalAmount.toFloat()
            ,mSalesTaxAmount.toFloat()
            ,"New_Order"
            ,menuOrderedList
            , Delivery()
            ,ownerId
            ,mDiscountTotal.toFloat()
            ,mServicesCharges.toFloat()
            ,""
            ,""
            ,""
            ,""
            ,AppGlobal.readString(requireActivity(),AppGlobal.customerMobile,"0"))
        Timber.d("Checkout data: ${Gson().toJson(mCheckout)}")
        (activity as CustomerHomeActivity).mModel.updateCheckout(mCheckout)
        redirectCheckout()
    }

    private fun redirectCheckout() {
        (activity as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_checkout), isProfileMenuVisible = false, locationVisibility = false,isMenuVisibility = false)
                (activity as CustomerHomeActivity?)?.loadNewFragment(
                    CheckoutFragment(),
                    "checkout"
                )
    }

    /*****************************************************************************************************************************/
    //                                      Room Database Section
    /*****************************************************************************************************************************/
    //                                    1:- Get Cart Record from Room Database
    //                                    2:- Update Cart in Room Database
    //                                    3:- Delete Item from Room Database
    //                                    4:- Update Cart Badge
    /*****************************************************************************************************************************/

    private fun getCartRecord(){
        val cartLiveData=databaseCreator.cartDao.fetch(restaurantId)

        cartLiveData.observe(requireActivity(), Observer {

            cartList= it as ArrayList<Cart>
            mBinding.btnPayToProceed.isEnabled = cartList.size > 0

            cartItemAdapter.updateList(it as ArrayList<Cart>)
            calculatePrice()
            setViews()
               //(requireActivity()as CustomerHomeActivity).updateBottomNavigationCount(cartList.size)
                cartLiveData.removeObservers(requireActivity())


        })
    }
    private fun updateCart(cart: Cart) {
        viewModel.updateCart(cart)
        updateCartBadge()
    }

    private fun deleteItemRow(cart:Cart){
        viewModel.deleteCartItem(cart)
        updateCartBadge()
    }

    private fun updateCartBadge() {
        Handler(Looper.getMainLooper()).postDelayed({
            val cartLiveData=databaseCreator.cartDao.fetch(restaurantId)
            cartLiveData.observe(requireActivity(), Observer {
                if (it != null) {
                    val cartArray = it as ArrayList<Cart>
                    getCartRecord()
                    (requireActivity() as CustomerHomeActivity).updateBottomNavigationCount(cartArray.size)
                }
                cartLiveData.removeObservers(requireActivity())
            })
        },100)

    }

}