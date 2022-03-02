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
import com.comcept.rmrscustomer.utilities.AppGlobal
import com.kaopiz.kprogresshud.KProgressHUD

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
        progressDialog.setLabel("Please Wait")
        progressDialog.show()
        viewModel.verifyInvoiceResponse(verifyInvoice).observe(requireActivity(), { it ->
            progressDialog.dismiss()
            if (it != null && it.message == "Success") {
//                AppGlobal.showDialog(
//                    getString(R.string.title_alert),
//                    it.data.CustomerID.toString(),
//                    requireActivity()
//                )


                it.data?.let {
                    mbinding.restaurantNameTxt.text = it.RestaurantName
                    mbinding.InvoiceIdTxt.text = it.InvoiceID
                    mbinding.dateTxt.text = it.InvoiceCreated
                    mbinding.servicesChargesTxt.text = it.ServiceCharges.toString()
                    mbinding.totalEnTxt.text = it.SubTotal.toString()
                    mbinding.saleTaxTxt.text = it.SalesTax.toString()
                    mbinding.totalInTxt.text = it.TotalAmount.toString()

                    val discount =
                        ((it.Discount?.AmountBeforeDiscount)?.times((it.Discount?.DiscountPercentage!!))
                            ?.div(100))?.toDouble()
                    mbinding.discountTxt.text = discount.toString()
                    mbinding.discountTotalTxt.text = it.Discount?.AmountAfterDiscount.toString()

                    mbinding.edtxtResturantId.text?.clear()
                    mbinding.edtxtInvoiceId.text?.clear()
                }

            } else {


                it.message?.let { it1 ->
                    AppGlobal.showDialog(
                        getString(R.string.title_alert),
                        it1,
                        requireActivity()
                    )
                }
            }
        })
    }


}