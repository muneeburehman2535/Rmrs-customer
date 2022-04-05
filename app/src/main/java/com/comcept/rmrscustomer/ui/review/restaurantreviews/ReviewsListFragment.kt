package com.comcept.rmrscustomer.ui.review.restaurantreviews

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaopiz.kprogresshud.KProgressHUD
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.adapters.ReviewListAdapter
import com.comcept.rmrscustomer.data_class.review.Data
import com.comcept.rmrscustomer.data_class.review.Review
import com.comcept.rmrscustomer.databinding.ReviewsListFragmentBinding
import com.comcept.rmrscustomer.repository.Response
import com.comcept.rmrscustomer.ui.home.CustomerHomeActivity
import com.comcept.rmrscustomer.ui.review.ReviewViewModel
import com.comcept.rmrscustomer.utilities.AppGlobal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ReviewsListFragment : Fragment() {

    private lateinit var viewModel: ReviewViewModel
    private lateinit var mBinding: ReviewsListFragmentBinding
    private lateinit var reviewListAdapter: ReviewListAdapter
    private lateinit var reviewList: ArrayList<Data>
    private lateinit var restaurantId: String
    private lateinit var progressDialog: KProgressHUD



    val job  = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main+job)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.reviews_list_fragment, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)
        progressDialog = AppGlobal.setProgressDialog(requireActivity())
        setRecyclerViewAdapter()
        getRestaurantId()
    }

    private fun getRestaurantId() {
//        (activity as CustomerHomeActivity?)?.mModel?.restaurantID?.observe(viewLifecycleOwner, Observer {
//            this.restaurantId=it
//
//            getRestaurantDetail(restaurantId)
//        })
        restaurantId = AppGlobal.readString(requireActivity(), AppGlobal.restaurantId, "")
        if (AppGlobal.isInternetAvailable(requireActivity())) {
            getRestaurantDetail(restaurantId)
        } else {
            AppGlobal.snackBar(
                mBinding.layoutParentRlf,
                getString(R.string.err_no_internet),
                AppGlobal.SHORT
            )
        }

    }

    private fun setRecyclerViewAdapter() {
        reviewList = arrayListOf()
        reviewListAdapter = ReviewListAdapter(reviewList)
        val layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL,
            false
        )
        mBinding.rvReviews.layoutManager = layoutManager
        mBinding.rvReviews.adapter = reviewListAdapter
    }


    /*
   * Login API Method
   * */
    @SuppressLint("SetTextI18n")
    private fun getRestaurantDetail(restaurantID: String) {


        uiScope.launch {
            viewModel.getReviewListResponse(restaurantID).observe(requireActivity(), {

                when(it){

                    is Response.Loading ->{
                        progressDialog.setLabel("Please Wait")
                        progressDialog.show()

                    }

                    is Response.Success ->{

                        it.data?.let {
                            progressDialog.dismiss()
                            if (it != null && it.Message == "Success") {
                                reviewList = it.data
                                mBinding.txtReviewTotal.text = "${reviewList.size} Reviews"
                                if (reviewList.size > 0) {
                                    mBinding.layoutReviewList.visibility = View.VISIBLE
                                    mBinding.layoutNotFoundRlf.visibility = View.GONE
                                    reviewListAdapter.updateReviewList(reviewList)
                                } else {
                                    mBinding.layoutReviewList.visibility = View.GONE
                                    mBinding.layoutNotFoundRlf.visibility = View.VISIBLE
                                }

                            }
//            else{
//                AppGlobal.showDialog(getString(R.string.title_alert),it.data.description,requireContext())
//            }
                        }

                    }


                    is Response.Error ->{

                        AppGlobal.showDialog(getString(R.string.title_alert), it.message.toString(),requireActivity())
                        if (progressDialog.isShowing) {
                            progressDialog.dismiss()

                        }
                    }

                }


            })
        }


    }

}