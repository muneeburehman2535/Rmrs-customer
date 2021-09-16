package com.comcept.rmrs_customer.data_class.review.reviewlist

import com.comcept.rmrs_customer.data_class.review.Data

data class ReviewListResponse(
        val Message:String,
        val data:ArrayList<Data>
)
