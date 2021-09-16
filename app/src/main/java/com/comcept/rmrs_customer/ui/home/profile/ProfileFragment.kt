package com.comcept.rmrs_customer.ui.home.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.kaopiz.kprogresshud.KProgressHUD
import com.comcept.rmrs_customer.R
import com.comcept.rmrs_customer.data_class.login.Login
import com.comcept.rmrs_customer.data_class.profile.Profile
import com.comcept.rmrs_customer.databinding.ProfileFragmentBinding
import com.comcept.rmrs_customer.ui.home.CustomerHomeActivity
import com.comcept.rmrs_customer.utilities.AppGlobal

class ProfileFragment : Fragment(),View.OnClickListener {
    private var isEditProfile:Boolean=false
    private lateinit var mBinding:ProfileFragmentBinding
    private lateinit var customerName:String
    private lateinit var customerEmail:String
    private lateinit var customerMobile:String
    private lateinit var progressDialog: KProgressHUD
    private val mAwesomeValidation = AwesomeValidation(ValidationStyle.BASIC)
    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding=DataBindingUtil.inflate(inflater,R.layout.profile_fragment,container,false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        mBinding.profileViewModel=viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog=AppGlobal.setProgressDialog(requireActivity())
        (context as CustomerHomeActivity?)?.mModel?.mEditProfile?.observe(viewLifecycleOwner, Observer {
            if (it)
            {
                mBinding.txtNameAp.isEnabled=true
                mBinding.txtNameAp.requestFocus()
                mBinding.txtNameAp.setBackgroundResource(R.drawable.edit_text_background)
                mBinding.txtNumberAp.isEnabled=true
                mBinding.txtNumberAp.setBackgroundResource(R.drawable.edit_text_background)
                mBinding.btnUpdatePf.visibility=View.VISIBLE
                (context as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_edit_profile), isProfileMenuVisible = false, locationVisibility = false,isMenuVisibility = false)
            }
        })
        getCustomerData()
        setViews()
        mBinding.btnUpdatePf.setOnClickListener(this)
    }

    private fun setViews() {
        mBinding.txtNameAp.setText(customerName)
        mBinding.txtNumberAp.setText(customerMobile)
        mBinding.txtEmailAp.text = customerEmail
    }

    private fun getCustomerData() {
        customerName=AppGlobal.readString(requireActivity(),AppGlobal.customerName,"")
        customerEmail=AppGlobal.readString(requireActivity(),AppGlobal.customerEmail,"")
        customerMobile=AppGlobal.readString(requireActivity(),AppGlobal.customerMobile,"")
    }

    /*
    * Check following credentials:
    * 1:- Full name.
    * 2:- Mobile Number
    * */
    private fun checkCredentials(){
        mAwesomeValidation.addValidation(mBinding.txtNameAp,"[a-zA-Z ]+",getString(R.string.err_full_name))
        mAwesomeValidation.addValidation(mBinding.txtNumberAp, Patterns.PHONE,getString(R.string.err_mobile))
    }


    //*************************************************************************************************************************************************/
    //                                                                API Calls Section:
    //************************************************************************************************************************************************/

    /*
    * Login API Method
    * */
    private fun updateUser(){
        progressDialog.setLabel("Please Wait")
        progressDialog.show()
        val profile= Profile(AppGlobal.readString(requireActivity(),AppGlobal.customerId,"0"),mBinding.txtNumberAp.text?.trim().toString(), mBinding.txtNameAp.text?.trim().toString())
        viewModel.updateProfileResponse(profile).observe(this, {
            progressDialog.dismiss()
            if (it.Message=="Success")
            {
                AppGlobal.writeString(requireActivity(),AppGlobal.customerName, it.data.result.Name)
                AppGlobal.writeString(requireActivity(),AppGlobal.customerMobile,it.data.result.MobileNumber)
                mBinding.btnUpdatePf.visibility=View.GONE

                mBinding.txtNameAp.isEnabled=false
                //mBinding.txtNameAp.requestFocus()
                mBinding.txtNameAp.setBackgroundResource(0)
                mBinding.txtNumberAp.isEnabled=false
                mBinding.txtNumberAp.setBackgroundResource(0)
                (requireActivity() as CustomerHomeActivity).editProfileMenu?.isVisible=true


                //AppGlobal.writeString(this,AppGlobal.customerId,"ai0anPGypI")
                // AppGlobal.startNewActivity(this, CustomerHomeActivity::class.java)
                //finishAffinity()
            }
            else{
                AppGlobal.showDialog(getString(R.string.title_alert),it.data.description,requireActivity())
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_update_pf->{
                checkCredentials()
                if (mAwesomeValidation.validate())
                {
                    updateUser()
                }
            }
        }
    }

}