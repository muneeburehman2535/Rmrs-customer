package com.comcept.rmrscustomer.ui.home.verifyInvoice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.adapters.MyReservationsAdapter
import com.comcept.rmrscustomer.adapters.RestaurantListAdapter
import com.comcept.rmrscustomer.data_class.myorders.currentorders.CurrentOrderDataClass
import com.comcept.rmrscustomer.data_class.verifyInvoice.RestaurantList
import com.comcept.rmrscustomer.data_class.verifyInvoice.VerifyInvoice
import com.comcept.rmrscustomer.databinding.FragmentRestaurantListInvoiceBinding
import com.comcept.rmrscustomer.repository.Response
import com.comcept.rmrscustomer.ui.home.CustomerHomeActivity
import com.comcept.rmrscustomer.utilities.AppGlobal
import com.google.gson.Gson
import com.kaopiz.kprogresshud.KProgressHUD
import timber.log.Timber
import java.util.*

class RestaurantListInvoiceFragment : Fragment(),RestaurantListListener {

    companion object {

    }

    private lateinit var viewModel: VerifyInvoiceViewModel
    private lateinit var progressDialog: KProgressHUD

    lateinit var mbinding: FragmentRestaurantListInvoiceBinding
    private lateinit var restaurantListAdapter: RestaurantListAdapter
    private lateinit var restaurantList: ArrayList<RestaurantList>
    private lateinit var tempArrayList: ArrayList<RestaurantList>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mbinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant_list_invoice, container, false)
        return mbinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(VerifyInvoiceViewModel::class.java)

        progressDialog = AppGlobal.setProgressDialog(requireActivity())

        tempArrayList = arrayListOf()

        setRestaurantListAdapter()

        getRestaurantList()
    }


    private fun setRestaurantListAdapter() {
        restaurantList= arrayListOf()
        restaurantListAdapter = RestaurantListAdapter(requireActivity(), restaurantList)
        mbinding.rvFrli.layoutManager = LinearLayoutManager(
            requireActivity(),
        )
        mbinding.rvFrli.adapter = restaurantListAdapter
        RestaurantListAdapter.setRestaurantClickListener(this)

        mbinding.idSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {


                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {



                tempArrayList.clear()

                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()){

                    restaurantList.forEach {
                        if (it.RestaurantName.lowercase(Locale.getDefault()).contains(searchText)){

                            tempArrayList.add(it)
                        }
                    }


                    restaurantListAdapter.updateRestaurantList(tempArrayList)
                }

                else{

                    tempArrayList.clear()
                    tempArrayList.addAll(restaurantList)
                    restaurantListAdapter.updateRestaurantList(tempArrayList)
                }

//                restaurantListAdapter.updateRestaurantList(restaurantList.filter {
//                    it.RestaurantName?.lowercase()
//                        ?.startsWith(
//                            newText.toString().lowercase()
//                        ) || it.City.lowercase()
//                        .startsWith(newText.toString().lowercase())
//                } as ArrayList<RestaurantList>)

                return false
            }
        })


    }





    private fun getRestaurantList() {

        viewModel.getRestaurantList().observe(requireActivity()) { it ->


            when (it) {

                is Response.Loading -> {

                    progressDialog.setLabel("Please Wait")
                    progressDialog.show()
                }

                is Response.Success -> {
                    progressDialog.dismiss()
                    it.data?.let {
                        if (it != null && it.Message == "Success") {


                            it.data.let {

                                this@RestaurantListInvoiceFragment.restaurantList = it
                                Timber.d("Checkout API: ${Gson().toJson(it)}")

                                restaurantListAdapter.updateRestaurantList(it)
                                


                            }

                        } else {

                            AppGlobal.showDialog(
                                getString(R.string.title_alert),
                                "Data Not Found",
                                requireActivity()
                            )

                        }
                    }

                }

                is Response.Error -> {

                    AppGlobal.showDialog(
                        getString(R.string.title_alert), it.message.toString(),
                        requireActivity()
                    )

                    if (progressDialog.isShowing) {

                        progressDialog.dismiss()

                    }
                }
            }

        }
    }



    override fun onRestaurantClickListener(position: Int) {
        val mfragment = VerifyInvoiceFragment()
        val bundle = Bundle()
        bundle.putBoolean("isverifyInvoice", true)
        bundle.putSerializable(
            "restaurant_detail_items",
            tempArrayList[position]
        )
        mfragment.arguments = bundle
        ((requireActivity() as CustomerHomeActivity).replaceNewFragment(mfragment))
    }

}