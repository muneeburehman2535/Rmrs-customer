package com.teletaleem.rmrs_customer.ui.home.favourite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kaopiz.kprogresshud.KProgressHUD
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.CategoriesAdapter
import com.teletaleem.rmrs_customer.adapters.FavouriteAdapter
import com.teletaleem.rmrs_customer.databinding.FavouriteFragmentBinding
import com.teletaleem.rmrs_customer.db.CustomerDatabase
import com.teletaleem.rmrs_customer.db.dataclass.Favourite
import com.teletaleem.rmrs_customer.ui.home.CustomerHomeActivity
import com.teletaleem.rmrs_customer.ui.restauratntdetail.RestaurantDetailFragment
import com.teletaleem.rmrs_customer.utilities.AppGlobal
import com.teletaleem.rmrs_customer.utilities.RecyclerItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private lateinit var mFavouriteBinding:FavouriteFragmentBinding
    private lateinit var mViewModel: FavouriteViewModel
    private lateinit var  databaseCreator: CustomerDatabase
    private lateinit var progressDialog: KProgressHUD
    private lateinit var favouriteAdapter:FavouriteAdapter
    private lateinit var favouriteList:ArrayList<Favourite>

    companion object {
        fun newInstance() = FavouriteFragment()
    }

    private lateinit var viewModel: FavouriteViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        mFavouriteBinding=DataBindingUtil.inflate(inflater,R.layout.favourite_fragment,container,false)
        return mFavouriteBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)
        setFavouriteAdapter()
        mFavouriteBinding.favouriteViewModel=mViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog= AppGlobal.setProgressDialog(requireActivity())
        databaseCreator= CustomerDatabase.getInstance(requireActivity())
        checkRestaurantById()
    }

    private fun setFavouriteAdapter() {
        favouriteList= arrayListOf()
        favouriteAdapter = FavouriteAdapter(requireActivity(),favouriteList)
        mFavouriteBinding.rvFavourite.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
        )
        mFavouriteBinding.rvFavourite.adapter = favouriteAdapter
        setRecyclerViewListener(mFavouriteBinding.rvFavourite)


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
                        (activity as CustomerHomeActivity).mModel.updateRestaurantId(favouriteList[position].restaurant_id)
                        AppGlobal.writeString(requireActivity(),AppGlobal.restaurantId,favouriteList[position].restaurant_id)
                        (activity as CustomerHomeActivity).mModel.updateRestaurantName(favouriteList[position].restaurant_name)
                        (activity as CustomerHomeActivity).changeToolbarName(getString(R.string.title_restaurants), isProfileMenuVisible = false, locationVisibility = false,isMenuVisibility = true)
                        (activity as CustomerHomeActivity).loadNewFragment(
                                RestaurantDetailFragment(),
                                "restaurant_detail"
                        )
                    }

                })
        )
    }


    private fun checkRestaurantById(){
        val favouriteLiveData=databaseCreator.favouriteDao.findRestaurants()

        favouriteLiveData.observe(requireActivity(), Observer {

            favouriteList= it as ArrayList<Favourite>

            favouriteAdapter.updateList(favouriteList)

        })
    }

}