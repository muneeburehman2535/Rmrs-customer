package com.teletaleem.rmrs_customer.ui.search.simple_search

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
import com.teletaleem.rmrs_customer.adapters.RestaurantAdapter
import com.teletaleem.rmrs_customer.adapters.SearchResultAdapter
import com.teletaleem.rmrs_customer.data_class.restaurant.RestaurantDataClass
import com.teletaleem.rmrs_customer.databinding.SimpleSearchFragmentBinding
import com.teletaleem.rmrs_customer.ui.home.CustomerHomeActivity
import com.teletaleem.rmrs_customer.ui.restauratntdetail.RestaurantDetailFragment
import com.teletaleem.rmrs_customer.utilities.RecyclerItemClickListener

class SimpleSearchFragment : Fragment() {
    private lateinit var mBinding:SimpleSearchFragmentBinding
    private lateinit var searchResultList:ArrayList<RestaurantDataClass>
    companion object {
        fun newInstance() = SimpleSearchFragment()
    }

    private lateinit var viewModel: SimpleSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding=DataBindingUtil.inflate(inflater,R.layout.simple_search_fragment,container,false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SimpleSearchViewModel::class.java)
        mBinding.simpleSearchViewModel=viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListItems()
        setSearchResultAdapter()
    }

    private fun setListItems() {
        searchResultList= arrayListOf()

        val restaurantDataClass=RestaurantDataClass("Arizona Grills","Ring Road, Cant Area Peshawar","4.9","(79)","")
        searchResultList.add(restaurantDataClass)

        val restaurantDataClass1=RestaurantDataClass("Habibi Restaurant","Sadar Area Peshawar","4.5","(121)","")
        searchResultList.add(restaurantDataClass1)

        val restaurantDataClass2=RestaurantDataClass("Jaleel Kabbab","Ring Road, Cant Area Peshawar","4.0","(200)","")
        searchResultList.add(restaurantDataClass2)
    }


    private fun setSearchResultAdapter() {
        val searchResultAdapter = SearchResultAdapter(requireContext(),searchResultList)
        mBinding.rvSearch.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        mBinding.rvSearch.adapter = searchResultAdapter
        setRecyclerViewListener(mBinding.rvSearch)
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
                        (context as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_restaurants))
                        (context as CustomerHomeActivity?)?.loadNewFragment(
                            RestaurantDetailFragment(),
                            "restaurant_detail"
                        )
                    }

                })
        )
    }

}