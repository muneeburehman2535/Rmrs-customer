package com.teletaleem.rmrs_customer.ui.search.simple_search

import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kaopiz.kprogresshud.KProgressHUD
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.SearchResultAdapter
import com.teletaleem.rmrs_customer.data_class.home.restaurants.Restaurants
import com.teletaleem.rmrs_customer.data_class.restaurant.RestaurantDataClass
import com.teletaleem.rmrs_customer.data_class.review.Review
import com.teletaleem.rmrs_customer.databinding.SimpleSearchFragmentBinding
import com.teletaleem.rmrs_customer.ui.home.CustomerHomeActivity
import com.teletaleem.rmrs_customer.ui.restauratntdetail.RestaurantDetailFragment
import com.teletaleem.rmrs_customer.utilities.AppGlobal
import com.teletaleem.rmrs_customer.utilities.RecyclerItemClickListener


class SimpleSearchFragment : Fragment() {
    private lateinit var mBinding:SimpleSearchFragmentBinding
    private lateinit var searchResultList:ArrayList<Restaurants>
    private lateinit var progressDialog: KProgressHUD
    private lateinit var searchResultAdapter:SearchResultAdapter
    companion object {
        fun newInstance() = SimpleSearchFragment()
    }

    private lateinit var viewModel: SimpleSearchViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        mBinding=DataBindingUtil.inflate(inflater, R.layout.simple_search_fragment, container, false)
        return mBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchResultList= arrayListOf()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SimpleSearchViewModel::class.java)
        mBinding.simpleSearchViewModel=viewModel
        setSearchResultAdapter()
        mBinding.edtxtSearchFragment?.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode === KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                if (v.text.toString().length >= 3) {
                    getSearchResult(v.text.toString())
                } else {
                    //no string
                }
            }
            false
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog=AppGlobal.setProgressDialog(requireActivity())




    }

    override fun onResume() {
        super.onResume()
        (activity as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.hint_search_food), isProfileMenuVisible = false, locationVisibility = true)
    }



    private fun setSearchResultAdapter() {

        searchResultAdapter = SearchResultAdapter(requireContext(), searchResultList)
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
                        (activity as CustomerHomeActivity).mModel.updateRestaurantId(searchResultList[position].RestaurantID)
                        AppGlobal.writeString(requireActivity(),AppGlobal.restaurantId,searchResultList[position].RestaurantID)
                        (activity as CustomerHomeActivity).mModel.updateRestaurantName(searchResultList[position].RestaurantName)
                        (context as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_restaurants), isProfileMenuVisible = false, locationVisibility = false)
                        (context as CustomerHomeActivity?)?.loadNewFragment(
                                RestaurantDetailFragment(),
                                "restaurant_detail"
                        )
                    }

                })
        )
    }


    //*************************************************************************************************************************************************/
    //                                                                API Calls Section:
    //************************************************************************************************************************************************/

    /*
    * Search API Method
    * */
    private fun getSearchResult(searchQuery: String){
        progressDialog.setLabel("Please Wait")
        progressDialog.show()
        viewModel.getSearchResponse(searchQuery).observe(requireActivity(), {
            progressDialog.dismiss()
            searchResultList=it.data.result
            searchResultAdapter.updateSearchList(searchResultList)

        })
    }

}