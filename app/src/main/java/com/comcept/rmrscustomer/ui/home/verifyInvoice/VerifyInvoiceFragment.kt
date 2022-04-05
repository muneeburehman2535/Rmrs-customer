package com.comcept.rmrscustomer.ui.home.verifyInvoice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.data_class.verifyInvoice.VerifyInvoice
import com.comcept.rmrscustomer.databinding.FragmentVerifyInvoiceBinding
import com.comcept.rmrscustomer.repository.Response
import com.comcept.rmrscustomer.utilities.AppGlobal
import com.kaopiz.kprogresshud.KProgressHUD
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class VerifyInvoiceFragment : Fragment(), View.OnClickListener {
    private lateinit var viewModel: VerifyInvoiceViewModel
    private lateinit var progressDialog: KProgressHUD

    lateinit var mbinding: FragmentVerifyInvoiceBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mbinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_verify_invoice, container, false)
        return mbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressDialog = AppGlobal.setProgressDialog(requireActivity())
        mbinding.btnVerifyAl.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VerifyInvoiceViewModel::class.java)
        mbinding.verifyInvoiceViewModel = viewModel
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_verify_al -> {


                if (mbinding.edtxtInvoiceId.text.isEmpty()) {


                    AppGlobal.showToast("Please enter invoice Id", requireContext())
                } else if (mbinding.edtxtResturantId.text.isEmpty()) {

                    AppGlobal.showToast("Please enter Restaurant Id", requireContext())

                } else {

                    val verifyinvoice = VerifyInvoice(

                        mbinding.edtxtResturantId.text.toString(),
                        mbinding.edtxtInvoiceId.text.toString()
                    )

                    if (AppGlobal.isInternetAvailable(requireActivity())) {
                        verifyInvoiceResult((verifyinvoice))
                    } else {
                        AppGlobal.snackBar(
                            mbinding.layoutParentUpf,
                            getString(R.string.err_no_internet),
                            AppGlobal.SHORT
                        )
                    }


                }

            }
        }
    }


    private fun verifyInvoiceResult(verifyInvoice: VerifyInvoice) {

        viewModel.verifyInvoiceResponse(verifyInvoice).observe(requireActivity()) { it ->


            when (it) {

                is Response.Loading -> {

                    progressDialog.setLabel("Please Wait")
                    progressDialog.show()
                }

                is Response.Success -> {
                    progressDialog.dismiss()
                    it.data?.let {
                        if (it != null && it.message == "Success") {


                            it.data?.let {
                                mbinding.restaurantNameTxt.text = it.RestaurantName
                                mbinding.InvoiceIdTxt.text = it.InvoiceID


                                val dateArr = it.InvoiceCreated!!.split("T")

                                mbinding.dateTxt.text = dateArr[0]

                                if (it.ServiceCharges!! >0.0){
                                    mbinding.servicesChargesTxt.text = it.ServiceCharges.toString()
                                }
                                else{
                                    mbinding.serviceChargesTitle.text = "Delivery Charges"
                                    mbinding.servicesChargesTxt.text = it.DeliveryCharges.toString()
                                }

                                mbinding.totalEnTxt.text = it.SubTotal.toString()
                                mbinding.saleTaxTxt.text = it.SalesTax.toString()
                                mbinding.totalInTxt.text = it.TotalAmount.toString()

                                val discount = it.Discount?.OtherDiscount

                                if (discount != null) {
                                    if (discount>0){

                                        mbinding.titleDiscount.visibility = View.VISIBLE
                                        mbinding.discountTxt.visibility = View.VISIBLE
                                        mbinding.discountTxt.text = discount.toString()

                                        mbinding.titleDiscountedTotal.visibility = View.VISIBLE
                                        mbinding.discountTotalTxt.visibility = View.VISIBLE
                                        mbinding.discountTotalTxt.text =
                                      it.Discount?.AmountAfterDiscount.toString()
                                    }
                                    else{

                                    }
                                }
//                                val discount =
//                                    ((it.Discount?.AmountBeforeDiscount)?.times((it.Discount?.DiscountPercentage!!))
//                                        ?.div(100))?.toDouble()
//                                mbinding.discountTxt.text = discount.toString()
//                                mbinding.discountTotalTxt.text =
//                                    it.Discount?.AmountAfterDiscount.toString()

                                mbinding.edtxtResturantId.text?.clear()
                                mbinding.edtxtInvoiceId.text?.clear()
                            }

                        } else {

                            AppGlobal.showDialog(
                                getString(R.string.title_alert),
                                "Data Not Found Please Check Your IDs",
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


}