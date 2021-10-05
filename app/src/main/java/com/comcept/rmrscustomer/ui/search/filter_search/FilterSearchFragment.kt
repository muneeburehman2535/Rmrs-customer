package com.comcept.rmrscustomer.ui.search.filter_search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.adapters.FilterCategoryAdapter
import com.comcept.rmrscustomer.data_class.filtersearch.FilterSearch
import com.comcept.rmrscustomer.databinding.FilterSearchFragmentBinding
import com.comcept.rmrscustomer.ui.home.CustomerHomeActivity
import com.comcept.rmrscustomer.ui.home.HomeFragment

class FilterSearchFragment : Fragment() {
    private lateinit var mBinding:FilterSearchFragmentBinding
    private lateinit var categoryList:ArrayList<FilterSearch>

    companion object {
        fun newInstance() = FilterSearchFragment()
    }

    private lateinit var viewModel: FilterSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding=DataBindingUtil.inflate(inflater,R.layout.filter_search_fragment,container,false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FilterSearchViewModel::class.java)
        mBinding.filterViewModel=viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCategoryList()
        setCategoryAdapter()
    }

    override fun onResume() {
        super.onResume()
        (activity as CustomerHomeActivity?)?.updateToolbarTitle(getString(R.string.title_filter),false, View.VISIBLE,true)
        //(context as CustomerHomeActivity?)?.updateToolbarAddress()
    }

    private fun setCategoryList() {
        categoryList= arrayListOf()

        val filterSearch=FilterSearch("1","All Food",false)
        categoryList.add(filterSearch)
        val filterSearch2=FilterSearch("2","Chinese",false)
        categoryList.add(filterSearch2)
        val filterSearch3=FilterSearch("3","Continental",false)
        categoryList.add(filterSearch3)
        val filterSearch4=FilterSearch("4","Pakistani",false)
        categoryList.add(filterSearch4)
    }

    private fun setCategoryAdapter() {
        val filterCategoryAdapter = FilterCategoryAdapter(requireContext(),categoryList)
        mBinding.rvCategoriesFilter.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        mBinding.rvCategoriesFilter.adapter = filterCategoryAdapter
        //setRecyclerViewListener(mBinding.rvRestaurants)
    }


}