package com.teletaleem.rmrs_customer.ui.updatepassword

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.databinding.UpdatePasswordFragmentBinding

class UpdatePasswordFragment : Fragment() {

    private lateinit var mBinding:UpdatePasswordFragmentBinding
    companion object {
        fun newInstance() = UpdatePasswordFragment()
    }

    private lateinit var viewModel: UpdatePasswordViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {

        mBinding=DataBindingUtil.inflate(inflater,R.layout.update_password_fragment,container,false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UpdatePasswordViewModel::class.java)
        mBinding.updatePasswordViewModel=viewModel
    }

}