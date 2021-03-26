package com.teletaleem.rmrs_customer.ui.restauratntdetail.menudetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.MenuAdapter
import com.teletaleem.rmrs_customer.adapters.RestaurantAdapter
import com.teletaleem.rmrs_customer.databinding.MenuDetailFragmentBinding
import com.teletaleem.rmrs_customer.ui.home.CustomerHomeActivity
import com.teletaleem.rmrs_customer.ui.home.cart.CartFragment
import com.teletaleem.rmrs_customer.ui.restauratntdetail.RestaurantDetailFragment
import com.teletaleem.rmrs_customer.utilities.RecyclerItemClickListener

class MenuDetailFragment : Fragment() {
    private lateinit var mBinding:MenuDetailFragmentBinding

    companion object {
        fun newInstance() = MenuDetailFragment()
    }

    private lateinit var viewModel: MenuDetailViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        mBinding=DataBindingUtil.inflate(inflater,R.layout.menu_detail_fragment,container,false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MenuDetailViewModel::class.java)
        mBinding.menuDetailViewModel=viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMenuAdapter()
    }

    private fun setMenuAdapter() {
        val menuAdapter = MenuAdapter(requireContext())
        mBinding.rvMenuDf.layoutManager = LinearLayoutManager(
                context,
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
                        requireContext(),
                        recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(view: View?, position: Int) {

                    }

                    override fun onItemClick(view: View, position: Int) {
                        (context as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_cart))
                        (context as CustomerHomeActivity?)?.loadNewFragment(
                                CartFragment(),
                                "cart"
                        )
                    }

                })
        )
    }

}