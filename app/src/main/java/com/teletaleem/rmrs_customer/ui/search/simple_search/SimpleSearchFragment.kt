package com.teletaleem.rmrs_customer.ui.search.simple_search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teletaleem.rmrs_customer.R

class SimpleSearchFragment : Fragment() {

    companion object {
        fun newInstance() = SimpleSearchFragment()
    }

    private lateinit var viewModel: SimpleSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.simple_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SimpleSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}