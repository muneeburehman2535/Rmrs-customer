package com.teletaleem.rmrs_customer.ui.home.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.databinding.ProfileFragmentBinding
import com.teletaleem.rmrs_customer.ui.home.CustomerHomeActivity

class ProfileFragment : Fragment() {
    private var isEditProfile:Boolean=false
    private lateinit var mBinding:ProfileFragmentBinding
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
        (context as CustomerHomeActivity?)?.mModel?.mEditProfile?.observe(viewLifecycleOwner, Observer {
            if (it)
            {
                mBinding.txtNameAp.isEnabled=true
                mBinding.txtNameAp.requestFocus()
                mBinding.txtNameAp.setBackgroundResource(R.drawable.edit_text_background)
                mBinding.txtNumberAp.isEnabled=true
                mBinding.txtNumberAp.setBackgroundResource(R.drawable.edit_text_background)
                (context as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_edit_profile), isProfileMenuVisible = false, locationVisibility = false)
            }
        })
    }

}