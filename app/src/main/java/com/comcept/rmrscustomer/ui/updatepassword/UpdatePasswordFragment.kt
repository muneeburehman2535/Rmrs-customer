package com.comcept.rmrscustomer.ui.updatepassword

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.kaopiz.kprogresshud.KProgressHUD
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.data_class.updatepassword.UpdatePassword
import com.comcept.rmrscustomer.databinding.UpdatePasswordFragmentBinding
import com.comcept.rmrscustomer.repository.Response
import com.comcept.rmrscustomer.utilities.AppGlobal

class UpdatePasswordFragment : Fragment(),View.OnClickListener {

    private lateinit var mBinding:UpdatePasswordFragmentBinding
    private lateinit var progressDialog: KProgressHUD
    private val mAwesomeValidation = AwesomeValidation(ValidationStyle.BASIC)
    companion object {
        fun newInstance() = UpdatePasswordFragment()
    }

    private lateinit var viewModel: UpdatePasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.update_password_fragment, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UpdatePasswordViewModel::class.java)
        mBinding.updatePasswordViewModel=viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog= AppGlobal.setProgressDialog(requireActivity())
        mBinding.btnUpdateUpf.setOnClickListener(this)
    }


    /*
   * Check following credentials:
   * 1:- Password
   * 2:- Confirm Password
   * */
    private fun checkCredentials() {
        mAwesomeValidation.addValidation(
            mBinding.edtxtOldPassword,
            "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}",
            getString(R.string.err_old_password)
        )

        mAwesomeValidation.addValidation(
            mBinding.edtxtPasswordUpf,
            "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}",
            getString(R.string.err_password)
        )
        mAwesomeValidation.addValidation(
            mBinding.edtxtConfirmPasswordUpf,
            mBinding.edtxtPasswordUpf,
            getString(R.string.err_password_confirmation)
        )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_update_upf -> {
                checkCredentials()
                if (mAwesomeValidation.validate()) {
                    val updatePassword = UpdatePassword(
                        AppGlobal.readString(
                            requireActivity(),
                            AppGlobal.customerId,
                            "0"
                        ),
                        mBinding.edtxtPasswordUpf.text.toString(),
                        mBinding.edtxtOldPassword.text.toString()
                    )
                    if (AppGlobal.isInternetAvailable(requireActivity())) {
                        updatePasswordResult((updatePassword))
                    } else {
                        AppGlobal.snackBar(
                            mBinding.layoutParentUpf,
                            getString(R.string.err_no_internet),
                            AppGlobal.SHORT
                        )
                    }

                }
            }
        }
    }


    //*************************************************************************************************************************************************/
    //                                                                API Calls Section:
    //************************************************************************************************************************************************/

    /*
    * Update Password API Method
    * */
    private fun updatePasswordResult(updatePassword: UpdatePassword) {

        viewModel.updatePasswordResponse(updatePassword).observe(requireActivity(), {

           when(it){


               is Response.Loading ->{
                   progressDialog.setLabel("Please Wait")
                   progressDialog.show()

               }
               is Response.Success ->{

                   it.data?.let {
                       progressDialog.dismiss()
                       if (it!=null&&it.Message=="Success"){
                           AppGlobal.showDialog(getString(R.string.title_alert),it.data.description,requireActivity())
                           mBinding.edtxtPasswordUpf.text?.clear()
                           mBinding.edtxtConfirmPasswordUpf.text?.clear()
                           mBinding.edtxtOldPassword.text?.clear()
                       } else {
                           AppGlobal.showDialog(
                               getString(R.string.title_alert),
                               it.data.description,
                               requireActivity()
                           )
                       }
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