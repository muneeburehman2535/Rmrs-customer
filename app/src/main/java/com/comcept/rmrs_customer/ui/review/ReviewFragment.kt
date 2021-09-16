package com.comcept.rmrs_customer.ui.review

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.kaopiz.kprogresshud.KProgressHUD
import com.comcept.rmrs_customer.R
import com.comcept.rmrs_customer.data_class.review.Review
import com.comcept.rmrs_customer.databinding.ReviewFragmentBinding
import com.comcept.rmrs_customer.ui.home.CustomerHomeActivity
import com.comcept.rmrs_customer.ui.home.HomeFragment
import com.comcept.rmrs_customer.utilities.AppGlobal

class ReviewFragment : Fragment(),View.OnClickListener {



    companion object {
        fun newInstance() = ReviewFragment()
    }

    private lateinit var viewModel: ReviewViewModel
    private var ratingCount:Int=0
    private var sumOfRating:Double=0.0
    private lateinit var restaurantID:String
    private lateinit var mBinding:ReviewFragmentBinding
    private lateinit var progressDialog: KProgressHUD

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        mBinding=DataBindingUtil.inflate(inflater,R.layout.review_fragment,container,false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog=AppGlobal.setProgressDialog(requireActivity())
        getRestaurantId()
        mBinding.btnSubmitReview.setOnClickListener(this)
    }

    private fun getRestaurantId(){
//        (activity as CustomerHomeActivity?)?.mModel?.restaurantID?.observe(viewLifecycleOwner, Observer {
//            this.restaurantID=it
//            getRatingCount()
//        })

        restaurantID=AppGlobal.readString(requireActivity(),AppGlobal.restaurantId,"0")
        getRatingCount()
    }

    private fun getRatingCount(){
        (activity as CustomerHomeActivity?)?.mModel?.ratingCount?.observe(viewLifecycleOwner, Observer {
            this.ratingCount=it
            getSumOfRating()
        })
    }
    private fun getSumOfRating(){
        (activity as CustomerHomeActivity?)?.mModel?.sumOfRating?.observe(viewLifecycleOwner, Observer {
            this.sumOfRating=it
        })
    }
    private fun setReviewJson(){

        val review=Review(mBinding.edtxtCustomerName.text.toString().trim(),mBinding.rbRatingFr.rating.toDouble(),mBinding.edtxtReview.text.toString().trim(),restaurantID,sumOfRating,ratingCount)

        getRestaurantDetail(review)
    }


    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btn_submit_review->{
                if (AppGlobal.isInternetAvailable(requireActivity())){
                    setReviewJson()
                }
                else{
                    AppGlobal.snackBar(mBinding.layoutParentRf,getString(R.string.err_no_internet),AppGlobal.SHORT)
                }

            }
        }
    }

    //*************************************************************************************************************************************************/
    //                                                                API Calls Section:
    //************************************************************************************************************************************************/

    /*
    * Login API Method
    * */
    private fun getRestaurantDetail(review: Review){
        progressDialog.setLabel("Please Wait")
        progressDialog.show()
        viewModel.getReviewResponse(review).observe(requireActivity(), {
            progressDialog.dismiss()
            if (it.Message=="Sucess")
            {
               // AppGlobal.showDialog(getString(R.string.title_alert),it.data.description,requireContext())
                val alertDialog = AlertDialog.Builder(requireActivity())
                alertDialog.setTitle(getString(R.string.title_alert))
                alertDialog.setMessage(it.data.description)
                alertDialog.setCancelable(false)
                alertDialog.setPositiveButton("Ok") { dialog, _ ->
                    (requireActivity() as CustomerHomeActivity).getCallerFragment()
                    (requireActivity() as CustomerHomeActivity).txtToolbarName.text =(requireActivity() as CustomerHomeActivity).mCurrentLocation
                    //getCallerFragment()
                    (requireActivity() as CustomerHomeActivity).setToolbarTitle("", HomeFragment(), false, View.VISIBLE, true,isMenuVisibility = false)
                    dialog.cancel()
                }
                val alertDialog1 = alertDialog.create()
                alertDialog1.show()

            }
            else{
                AppGlobal.showDialog(getString(R.string.title_alert),it.data.description,requireContext())
            }
        })
    }



}