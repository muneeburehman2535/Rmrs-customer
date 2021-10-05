package com.comcept.rmrscustomer.ui.restauratntdetail.variant

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.adapters.VariantAdapter
import com.comcept.rmrscustomer.data_class.cart.Cart
import com.comcept.rmrscustomer.data_class.restaurantdetail.Menu
import com.comcept.rmrscustomer.data_class.restaurantdetail.Variant
import com.comcept.rmrscustomer.databinding.VariantFragmentBinding
import com.comcept.rmrscustomer.db.CustomerDatabase
import com.comcept.rmrscustomer.ui.home.CustomerHomeActivity
import com.comcept.rmrscustomer.utilities.AppGlobal
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


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
            for (index in 0 until variantList.size){
                variantList[index].isChecked=false
            }
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

    private fun showErrorDialog(){
        val alertDialog: AlertDialog = MaterialAlertDialogBuilder(
            requireActivity(),
            R.style.MyRounded_MaterialComponents_MaterialAlertDialog
        ) // for fragment you can use getActivity() instead of this
            .setView(R.layout.layout_dialog) // custom layout is here
            .show()

        val btnClose = alertDialog.findViewById<Button>(R.id.btn_close_ld)
        val btnContinue=alertDialog.findViewById<Button>(R.id.btn_continue_ld)
        btnClose?.setOnClickListener(View.OnClickListener{
            alertDialog.cancel()
        })

        btnContinue?.setOnClickListener(View.OnClickListener {
            emptyCartRecord()
            Handler(Looper.getMainLooper()).postDelayed({addItemToCart()},100)
            alertDialog.cancel()
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setViews() {
        mBinding.txtTitleVariantFragment.text=menuItem.MenuName
        mBinding.txtTitleItemDescFv.text=menuItem.Description
        mBinding.txtTitleTotalItemPriceFv.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(variantList[0].CalculatedPrice.toDouble())
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
        mBinding.txtTitleTotalItemPriceFv.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(variantList[position].CalculatedPrice.toDouble())
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

                //getCartRecord()
                getAllCartRecord()
            }
        }
    }

    private fun addItemToCart() {
        val cart = Cart(menuItem.RestaurantID
            , restaurantName
            , menuItem.MenuName
            , menuItem.MenuID
            , menuItem.Description
            , variantList[variantPosition].CalculatedPrice.toString()
            , variantList[variantPosition].ItemPrice.toString()
            , mBinding.txtQuantityFv.text.toString()
            , menuItem.Image
            , menuItem.Description
            ,AppGlobal.readString(requireActivity(),AppGlobal.restaurantAddress,"")
            ,AppGlobal.readString(requireActivity(),AppGlobal.restaurantImage,"")
            ,Gson().toJson(variantList[variantPosition]).toString()
            ,variantList[variantPosition].VariantID,menuItem.isVariant.toString())
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
        val cartLiveData=databaseCreator.cartDao.fetchCartRecord(AppGlobal.readString(requireActivity(),AppGlobal.restaurantId,"0"), menuItem.MenuID,variantList[variantPosition].VariantID)

        cartLiveData.observe(requireActivity(), Observer {
            if (it!=null) {
                AppGlobal.showDialog(getString(R.string.title_alert), getString(R.string.err_already_added), requireActivity())
            } else {
                addItemToCart()

            }
            cartLiveData.removeObservers(requireActivity())
        })
    }
    private fun getAllCartRecord(){
        val cartLiveData=databaseCreator.cartDao.fetchAllRecord()
        cartLiveData.observe(requireActivity(), Observer {
            if(it!=null){
                val cartList= it as ArrayList<Cart>
                var isFound=false
                for (ind in 0 until cartList.size){
                    if(AppGlobal.readString(requireActivity(),AppGlobal.restaurantId,"0")==cartList[ind].restaurant_id){
                        isFound=true
                        break
                    }
                }
                if (!isFound&&cartList.size>0){
                    showErrorDialog()
                }
                else{
                    getCartRecord()
                }
            }
            else{
                getCartRecord()
            }

            cartLiveData.removeObservers(requireActivity())
        })
    }

    private fun emptyCartRecord() {
        viewModel.emptyCartRecord()
    }


}