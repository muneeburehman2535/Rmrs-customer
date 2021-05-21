package com.teletaleem.rmrs_customer.ui.restauratntdetail.variant

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.VariantAdapter
import com.teletaleem.rmrs_customer.data_class.cart.Cart
import com.teletaleem.rmrs_customer.data_class.restaurantdetail.Menu
import com.teletaleem.rmrs_customer.data_class.restaurantdetail.Variant
import com.teletaleem.rmrs_customer.databinding.VariantFragmentBinding
import com.teletaleem.rmrs_customer.db.CustomerDatabase
import com.teletaleem.rmrs_customer.ui.home.CustomerHomeActivity
import com.teletaleem.rmrs_customer.utilities.AppGlobal
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class VariantFragment : Fragment(),VariantAdapter.MenuSelectionListener,View.OnClickListener {



    companion object {
        fun newInstance() = VariantFragment()
    }

    private lateinit var viewModel: VariantViewModel
    private lateinit var mBinding:VariantFragmentBinding
    private lateinit var variantList:ArrayList<Variant>
    private lateinit var variantAdapter:VariantAdapter
    private lateinit var menuItem:Menu
    private var itemQuantity=1
    private var restaurantName=""
    private var variantPosition=0
    private lateinit var  databaseCreator: CustomerDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding= DataBindingUtil.inflate(inflater, R.layout.variant_fragment, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VariantViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseCreator= CustomerDatabase.getInstance(requireActivity())
        setVariantAdapter()
        getMenuItem()
        setClickListeners()
        getRestaurantName()
    }

    private fun setClickListeners() {
        mBinding.imgMinusFv.setOnClickListener(this)
        mBinding.imgPlusFv.setOnClickListener(this)
        mBinding.btnAddToCartFv.setOnClickListener(this)
    }

    private fun getMenuItem(){
        (activity as CustomerHomeActivity?)?.mModel?.menuItem?.observe(viewLifecycleOwner, Observer {
            this.menuItem=it
            variantList=menuItem.Variant
            variantList[0].isChecked=true
            variantAdapter.updateList(variantList)
            setViews()
        })
    }
    private fun getRestaurantName()
    {
        (activity as CustomerHomeActivity?)?.mModel?.restaurantName?.observe(viewLifecycleOwner, Observer {
            this.restaurantName = it
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setViews() {
        mBinding.txtTitleVariantFragment.text=menuItem.MenuName
        mBinding.txtTitleItemDescFv.text=menuItem.Description
        mBinding.txtTitleTotalItemPriceFv.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(variantList[0].ItemPrice.toDouble())
        mBinding.txtQuantityFv.text=itemQuantity.toString()
        val mVariantType="${getString(R.string.title_choose)} ${menuItem.MenuCategory}"
        //mBinding.txtTitleVariantTypeFv.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
        mBinding.txtTitleVariantTypeFv.text=mVariantType.capitalize(
            Locale.getDefault())
    }

    /**************************************************************************************************************************/
    //                                          Recyclerview Adapters
    /**************************************************************************************************************************/

    private fun setVariantAdapter() {
        variantList= arrayListOf()
        variantAdapter = VariantAdapter(requireActivity(), variantList)
        mBinding.rvVariantsFv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        mBinding.rvVariantsFv.adapter = variantAdapter
        variantAdapter.setUpdateItemQuantityListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onMenuSelectionClick(position: Int) {
        variantPosition=position
        mBinding.txtTitleTotalItemPriceFv.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(variantList[position].ItemPrice.toDouble())
        for (index in 0 until variantList.size)
        {
            variantList[index].isChecked=false
        }
        variantList[position].isChecked=true
        variantAdapter.updateList(variantList)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_minus_fv->{
                if (itemQuantity > 1) {
                    itemQuantity -= 1
                    mBinding.txtQuantityFv.text = itemQuantity.toString()
                }
            }
            R.id.img_plus_fv->{
                itemQuantity+= 1
                mBinding.txtQuantityFv.text = itemQuantity.toString()
            }
            R.id.btn_add_to_cart_fv->{

                getCartRecord()
            }
        }
    }

    private fun addItemToCart() {
        val cart = Cart(menuItem.RestaurantID
            , restaurantName
            , menuItem.MenuName
            , menuItem.MenuID
            , variantList[variantPosition].ItemName
            , variantList[variantPosition].ItemPrice.toString()
            , variantList[variantPosition].ItemPrice.toString()
            , mBinding.txtQuantityFv.text.toString()
            , menuItem.Image
            , variantList[variantPosition].ItemName
            ,AppGlobal.readString(requireActivity(),AppGlobal.restaurantAddress,"")
            ,AppGlobal.readString(requireActivity(),AppGlobal.restaurantImage,""))
        viewModel.insertCartItem(cart)
        updateCartBadge()
    }

    private fun updateCartBadge() {
        Handler(Looper.getMainLooper()).postDelayed({
            val cartLiveData=databaseCreator.cartDao.fetch(AppGlobal.readString(requireActivity(),AppGlobal.restaurantId,"0"))
            cartLiveData.observe(requireActivity(), Observer {
                if (it != null) {
                    val cartArray = it as ArrayList<Cart>
                    (requireActivity() as CustomerHomeActivity).updateBottomNavigationCount(cartArray.size)
                }
                (requireActivity() as CustomerHomeActivity).removeFragment()
                cartLiveData.removeObservers(requireActivity())
            })
        },100)

    }

    private fun getCartRecord() {
        val cartLiveData=databaseCreator.cartDao.fetchCartRecord(AppGlobal.readString(requireActivity(),AppGlobal.restaurantId,"0"), menuItem.MenuID)

        cartLiveData.observe(requireActivity(), Observer {

            if (it != null) {
                AppGlobal.showDialog(getString(R.string.title_alert), getString(R.string.err_already_added), requireActivity())
            } else {
                addItemToCart()

            }
            cartLiveData.removeObservers(requireActivity())
        })


    }


}