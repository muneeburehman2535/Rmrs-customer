package com.comcept.rmrscustomer.data_class.filtersearch

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilterSearch(
    val id:String,
    val name:String,
    val isChecked:Boolean
)
