package com.teletaleem.rmrs_customer.ui.restauratntdetail.menudetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.MenuAdapter
import com.teletaleem.rmrs_customer.data_class.cart.Cart
import com.teletaleem.rmrs_customer.data_class.restaurantdetail.Menu
import com.teletaleem.rmrs_customer.databinding.MenuDetailFragmentBinding
import com.teletaleem.rmrs_customer.db.CustomerDatabase
import com.teletaleem.rmrs_customer.ui.home.CustomerHomeActivity
import com.teletaleem.rmrs_customer.utilities.AppGlobal
import com.teletaleem.rmrs_customer.utilities.RecyclerItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuDetailFragment : Fragment() {
    private lateinit var mBinding:MenuDetailFragmentBinding
    private lateinit var menuList:ArrayList<Menu>

    private lateinit var menuAdapter: MenuAdapter
    private lateinit var  databaseCreator: CustomerDatabase
    private lateinit var alertDialog:AlertDialog
    private var restaurantId="0"
    private var restaurantName=""
    private lateinit var cartList:ArrayList<Cart>



    private lateinit var viewModel: MenuDetailViewModel

    companion object{
        lateinit var updatedMenuList: ArrayList<Menu>
        fun getInstance(position: Int,menuList: ArrayList<Menu>): Fragment {
            updatedMenuList=menuList
            val bundle = Bundle()
            bundle.putInt("pos", position)
            val tabFragment = MenuDetailFragment()
            tabFragment.setArguments(bundle)
            return tabFragment
        }
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        mBinding=DataBindingUtil.inflate(inflater, R.layout.menu_detail_fragment, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MenuDetailViewModel::class.java)
        mBinding.menuDetailViewModel=viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseCreator= CustomerDatabase.getInstance(requireActivity())
        setMenuAdapter()
        getMenuList()
        getRestaurantId()
        getRestaurantName()
        cartList= arrayListOf()
    }

    private fun getRestaurantId(){
        (activity as CustomerHomeActivity?)?.mModel?.restaurantID?.observe(viewLifecycleOwner, Observer {
            this.restaurantId = it
        })
    }

    private fun getRestaurantName()
    {
        (activity as CustomerHomeActivity?)?.mModel?.restaurantName?.observe(viewLifecycleOwner, Observer {
            this.restaurantName = it
        })
    }

    private fun getMenuList() {
        (activity as CustomerHomeActivity?)?.mModel?.menuList?.observe(viewLifecycleOwner, Observer {
            menuList = it
            //updatedMenuList= menuList.clone() as ArrayList<Menu>
            menuAdapter.updateList(menuList)
        })
    }

    private fun setMenuAdapter() {
        menuList= arrayListOf()
        menuAdapter = MenuAdapter(requireActivity(), menuList)
        mBinding.rvMenuDf.layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
        )
        mBinding.rvMenuDf.adapter = menuAdapter
        setRecyclerViewListener(mBinding.rvMenuDf)
    }

    /*
   * Set Click listener on Recycler view
   * */
    private fun setRecyclerViewListener(recyclerView: RecyclerView)
    {
        recyclerView.addOnItemTouchListener(
                RecyclerItemClickListener(
                        requireActivity(),
                        recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(view: View?, position: Int) {

                    }

                    override fun onItemClick(view: View, position: Int) {
//                        (context as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_cart))
//                        (context as CustomerHomeActivity?)?.loadNewFragment(
//                                CartFragment(),
//                                "cart"
//                        )
                        getCartRecord(position)

                    }

                })
        )
    }

    @SuppressLint("SetTextI18n")
    private fun showAddToCartDialog(menu: Menu) {
        var quantity=1
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())

// ...Irrelevant code for customizing the buttons and title
// ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.layout_cart_card, null)
        dialogBuilder.setView(dialogView)

        val txtItemName=dialogView.findViewById<TextView>(R.id.txt_item_title_lcc)
        val txtItemPrice=dialogView.findViewById<TextView>(R.id.txt_item_price_lcc)
        val txtItemDesc=dialogView.findViewById<TextView>(R.id.txt_item_desc_lcc)
        val txtItemIncQuantity=dialogView.findViewById<TextView>(R.id.txt_plus_lcc)
        val txtItemDecQuantity=dialogView.findViewById<TextView>(R.id.txt_minus_lcc)
        val txtItemQuantity=dialogView.findViewById<TextView>(R.id.txt_quantity_lcc)
        val btnAddToCart=dialogView.findViewById<Button>(R.id.btn_add_to_cart_lcc)

        txtItemName.text=menu.MenuName
        txtItemDesc.text=menu.Description
        txtItemPrice.text=AppGlobal.mCurrency+AppGlobal.roundTwoPlaces(menu.CalculatedPrice.toDouble())
        txtItemQuantity.text=quantity.toString()

        txtItemIncQuantity.setOnClickListener(View.OnClickListener {
            quantity += 1
            txtItemQuantity.text = quantity.toString()
        })
        txtItemDecQuantity.setOnClickListener(View.OnClickListener {
            if (quantity > 1) {
                quantity -= 1
                txtItemQuantity.text = quantity.toString()
            }
        })

        btnAddToCart.setOnClickListener(View.OnClickListener {



            val cart = Cart(menu.RestaurantID
                , restaurantName
                , menu.MenuName
                , menu.MenuID
                , menu.Description
                , menu.CalculatedPrice.toString()
                , menu.ItemPrice.toString()
                , txtItemQuantity.text.toString()
                , menu.Image
                , menu.Description
                ,AppGlobal.readString(requireActivity(),AppGlobal.restaurantAddress,"")
                ,AppGlobal.readString(requireActivity(),AppGlobal.restaurantImage,""))
            viewModel.insertCartItem(cart)

            updateCartBadge()
//            (activity as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_cart))
//            (activity as CustomerHomeActivity?)?.loadNewFragment(
//                    CartFragment(),
//                    "cart"
//            )
            alertDialog.dismiss()
        })


         alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun updateCartBadge() {
        val cartLiveData=databaseCreator.cartDao.fetch(restaurantId)
        cartLiveData.observe(requireActivity(), Observer {
            if (it != null) {
                val cartArray = it as ArrayList<Cart>
                (requireActivity() as CustomerHomeActivity).updateBottomNavigationCount(cartArray.size)
            }
            alertDialog.dismiss()
//            cartLiveData.removeObservers(requireActivity())
        })
    }

    private fun getCartRecord(position: Int) {
        val cartLiveData=databaseCreator.cartDao.fetchCartRecord(restaurantId, menuList[position].MenuID)

        cartLiveData.observe(requireActivity(), Observer {

            if (it != null) {
                AppGlobal.showDialog(getString(R.string.title_alert), getString(R.string.err_already_added), requireActivity())
            } else {
                showAddToCartDialog(menuList[position])

            }
            cartLiveData.removeObservers(requireActivity())
        })


    }



}