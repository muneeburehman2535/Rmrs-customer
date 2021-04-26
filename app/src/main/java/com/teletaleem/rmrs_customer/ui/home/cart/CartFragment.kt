package com.teletaleem.rmrs_customer.ui.home.cart

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.CartItemAdapter
import com.teletaleem.rmrs_customer.data_class.cart.Cart
import com.teletaleem.rmrs_customer.data_class.checkout.Checkout
import com.teletaleem.rmrs_customer.data_class.checkout.Delivery
import com.teletaleem.rmrs_customer.data_class.checkout.MenuOrdered
import com.teletaleem.rmrs_customer.databinding.CartFragmentBinding
import com.teletaleem.rmrs_customer.db.CustomerDatabase
import com.teletaleem.rmrs_customer.ui.checkout.CheckoutFragment
import com.teletaleem.rmrs_customer.ui.home.CustomerHomeActivity
import com.teletaleem.rmrs_customer.utilities.AppGlobal
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CartFragment : Fragment(),View.OnClickListener,CartItemAdapter.UpdateItemQuantityListener {
    private lateinit var mBinding:CartFragmentBinding
    private lateinit var cartList:ArrayList<Cart>
    private lateinit var cartItemAdapter:CartItemAdapter

    private var mSalesTax="7"
    private var mItemTotalAmount="0"
    private var mDiscountTotal="0"
    private var mTotalDiscountedAmount="0"
    private var mSalesTaxAmount="0"
    private var mServicesCharges="40"
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

        val cart=Cart("1","abc","Pizza BBQ","1","Size Regular, Wheat thin crust onion, jalapeno, Black olive","999","120","2","","test")
        cartList.add(cart)

        val cart1=Cart("1","abcs","Beef Burger","2","Grilled Beef, Fries, Black olive","500","80","1","","test")
        cartList.add(cart1)
    }

    private fun getRestaurantId(){
//        (activity as CustomerHomeActivity?)?.mModel?.restaurantID?.observe(viewLifecycleOwner, Observer {
//            this.restaurantId=it
//            getCartRecord()
//        })
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
//                (activity as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_checkout))
//                (activity as CustomerHomeActivity?)?.loadNewFragment(
//                    CheckoutFragment(),
//                    "checkout"
//                )
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
            //cartList.removeAt(position)


        }
        else{
            cartList[position].quantity=quantity.toString()
            cartList[position].quantity=quantity.toString()
            updateCart(cartList[position])
        }

        //cartItemAdapter.updateList(cartList)

//        calculatePrice()
//        setViews()
    }

    /*****************************************************************************************************************************/
    //                                    1:- Calculate Total Amounts
    //                                    2:- Set Amounts on Views
    /*****************************************************************************************************************************/


    private fun calculatePrice() {
        mItemTotalAmount="0"
        mDiscountTotal="0"
        for (index in 0 until cartList.size)
        {
            mItemTotalAmount= (mItemTotalAmount.toInt()+(cartList[index].original_price.toInt()*cartList[index].quantity.toInt())).toString()
            mDiscountTotal= (mDiscountTotal.toInt()+(cartList[index].item_price.toInt()*cartList[index].quantity.toInt())).toString()

        }
        mTotalDiscountedAmount = if (mItemTotalAmount.toInt()>mDiscountTotal.toInt())
        {
           (mItemTotalAmount.toInt()-mDiscountTotal.toInt()).toString()
        }
        else{
            mItemTotalAmount
        }

        mSalesTaxAmount=(((mSalesTax.toInt()*mTotalDiscountedAmount.toInt())/100)).toString()

        mTotalAmount=(mTotalDiscountedAmount.toInt()+mSalesTaxAmount.toInt()+mServicesCharges.toInt()).toString()
    }

    @SuppressLint("SetTextI18n")
    private fun setViews() {
        mBinding.txtItemTotalCf.text=AppGlobal.mCurrency+mItemTotalAmount
        mBinding.txtDiscountCf.text=AppGlobal.mCurrency+mDiscountTotal
        mBinding.txtTaxChrgCf.text=AppGlobal.mCurrency+mSalesTaxAmount
        mBinding.txtServiceChrgCf.text=AppGlobal.mCurrency+mServicesCharges
        mBinding.txtTotalPayCf.text=AppGlobal.mCurrency+mTotalAmount
        mBinding.txtRestaurantNameLcic.text=AppGlobal.readString(requireActivity(),AppGlobal.restaurantName,"")
        mBinding.txtRestaurantLocLcic.text=AppGlobal.readString(requireActivity(),AppGlobal.restaurantAddress,"")
        AppGlobal.loadImageIntoGlide(AppGlobal.readString(requireActivity(),AppGlobal.restaurantImage,""),mBinding.imgRestaurantLcic,requireActivity())
    }

    private fun bindCheckoutData(){

        val menuOrderedList= arrayListOf<MenuOrdered>()
        for (index in 0 until cartList.size)
        {
            val menuOrdered=MenuOrdered(cartList[index].menu_id,cartList[index].item_name,cartList[index].item_price,cartList[index].quantity,cartList[index].description)
            menuOrderedList.add(menuOrdered)
        }
        val mCheckout=Checkout(cartList[0].restaurant_id,cartList[0].restaurant_name,true,AppGlobal.readString(requireActivity(),AppGlobal.customerId,""),"Usama Wajid",mTotalAmount.toFloat(),mSalesTaxAmount.toFloat(),"New_Order",menuOrderedList, Delivery(),ownerId,mItemTotalAmount.toFloat(),mServicesCharges.toFloat(),"","")
        Timber.d("Checkout data: ${Gson().toJson(mCheckout)}")
        (activity as CustomerHomeActivity).mModel.updateCheckout(mCheckout)
        redirectCheckout()
    }

    private fun redirectCheckout() {
        (activity as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_checkout))
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
    /*****************************************************************************************************************************/

    private fun getCartRecord(){
        val cartLiveData=databaseCreator.cartDao.fetch(restaurantId)

        cartLiveData.observe(requireActivity(), Observer {

            cartList= it as ArrayList<Cart>
            if (cartList!=null)
            {
                cartItemAdapter.updateList(it as ArrayList<Cart>)
                calculatePrice()
                setViews()
               //(requireActivity()as CustomerHomeActivity).updateBottomNavigationCount(cartList.size)
                //cartLiveData.removeObservers(requireActivity())
            }

        })
    }
    private fun updateCart(cart: Cart) {
        viewModel.updateCart(cart)
        getCartRecord()
    }

    private fun deleteItemRow(cart:Cart){
        viewModel.deleteCartItem(cart)
        getCartRecord()
    }

}