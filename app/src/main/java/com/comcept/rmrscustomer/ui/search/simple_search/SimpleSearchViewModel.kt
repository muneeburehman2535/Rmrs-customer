package com.comcept.rmrscustomer.ui.search.simple_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.comcept.rmrscustomer.data_class.home.restaurants.Restaurants
import com.comcept.rmrscustomer.data_class.searchresponse.SearchResponse

import com.comcept.rmrscustomer.repository.SearchRepository

class SimpleSearchViewModel : ViewModel() {
    private var searchRepository: SearchRepository = SearchRepository()

    fun getSearchResponse(searchQuery: String): LiveData<SearchResponse> {
        return searchRepository.getSearchResponseLiveData(searchQuery)
    }
}