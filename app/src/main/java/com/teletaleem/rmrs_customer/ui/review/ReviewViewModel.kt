package com.teletaleem.rmrs_customer.ui.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.teletaleem.rmrs_customer.data_class.restaurantdetail.RestaurantDetailResponse
import com.teletaleem.rmrs_customer.data_class.review.Review
import com.teletaleem.rmrs_customer.data_class.review.ReviewResponse
import com.teletaleem.rmrs_customer.repository.RestaurantDetailRepository
import com.teletaleem.rmrs_customer.repository.ReviewRepository

class ReviewViewModel : ViewModel() {

    private var reviewRepository: ReviewRepository = ReviewRepository()
    fun getReviewResponse(review:Review): LiveData<ReviewResponse> {
        return reviewRepository.getReviewResponseLiveData(review)
    }

    fun getReviewListResponse(restaurantID:String): LiveData<ReviewResponse> {
        return reviewRepository.getReviewResponseLiveData(restaurantID)
    }
}