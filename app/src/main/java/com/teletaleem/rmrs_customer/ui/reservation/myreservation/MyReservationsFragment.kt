package com.teletaleem.rmrs_customer.ui.reservation.myreservation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaopiz.kprogresshud.KProgressHUD
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.MyReservationsAdapter
import com.teletaleem.rmrs_customer.adapters.PastOrdersAdapter
import com.teletaleem.rmrs_customer.data_class.reservation.Result
import com.teletaleem.rmrs_customer.databinding.MyReservationsFragmentBinding
import com.teletaleem.rmrs_customer.utilities.AppGlobal

class MyReservationsFragment : Fragment() {

    private lateinit var mBinding:MyReservationsFragmentBinding
    private lateinit var reservationList:ArrayList<Result>
    private lateinit var myReservationsAdapter: MyReservationsAdapter
    private lateinit var progressDialog: KProgressHUD

    companion object {
        fun newInstance() = MyReservationsFragment()
    }

    private lateinit var viewModel: MyReservationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        mBinding=DataBindingUtil.inflate(inflater,R.layout.my_reservations_fragment,container,false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyReservationsViewModel::class.java)
        getMyReservationsList(AppGlobal.readString(requireActivity(),AppGlobal.customerId,"0"))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog=AppGlobal.setProgressDialog(requireActivity())
        setMyReservationAdapter()

    }

    private fun setMyReservationAdapter() {
        reservationList= arrayListOf()
        myReservationsAdapter = MyReservationsAdapter(requireActivity(), reservationList)
        mBinding.rvReservationFrag.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        mBinding.rvReservationFrag.adapter = myReservationsAdapter
        //setRecyclerViewListener(mBinding.rvPastOrdersFmo,"past_order")
    }


    /*
* Get My Reservation API Method
* */
    private fun getMyReservationsList(customerId: String){
        progressDialog.setLabel("Please Wait")
        progressDialog.show()
        viewModel.getReviewListResponse(customerId).observe(requireActivity(), {
            progressDialog.dismiss()
            if (it.Message == "Success") {
                reservationList=it.data.result
                myReservationsAdapter.updateReservationList(reservationList)

            } else {
                AppGlobal.showDialog(
                    getString(R.string.title_alert),
                    it.data.description,
                    requireContext()
                )
            }
        })
    }

}