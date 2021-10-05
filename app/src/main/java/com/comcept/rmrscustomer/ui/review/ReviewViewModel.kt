package com.comcept.rmrscustomer.ui.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.comcept.rmrscustomer.data_class.restaurantdetail.RestaurantDetailResponse
import com.comcept.rmrscustomer.data_class.review.Review
import com.comcept.rmrscustomer.data_class.review.ReviewResponse
import com.comcept.rmrscustomer.data_class.review.reviewlist.ReviewListResponse
import com.comcept.rmrscustomer.repository.RestaurantDetailRepository
import com.comcept.rmrscustomer.repository.ReviewRepository

class ReviewViewModel : ViewModel() {

    private var reviewRepository: ReviewRepository = ReviewRepository()
    fun getReviewResponse(review:Review): LiveData<ReviewResponse> {
        return reviewRepository.getReviewResponseLiveData(review)
    }

    fun getReviewListResponse(restaurantID:String): LiveData<ReviewListResponse> {
        return reviewRepository.getReviewResponseLiveData(restaurantID)
    }
}