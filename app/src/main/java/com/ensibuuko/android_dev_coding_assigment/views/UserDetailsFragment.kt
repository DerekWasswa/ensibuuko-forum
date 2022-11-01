package com.ensibuuko.android_dev_coding_assigment.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.databinding.UserDetailsBinding
import com.ensibuuko.android_dev_coding_assigment.utils.Constants.TAG
import com.ensibuuko.android_dev_coding_assigment.utils.Resource
import com.ensibuuko.android_dev_coding_assigment.utils.gone
import com.ensibuuko.android_dev_coding_assigment.utils.visible
import com.ensibuuko.android_dev_coding_assigment.viewmodels.UsersViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailsFragment: BottomSheetDialogFragment() {
    private var _binding: UserDetailsBinding? = null

    private val binding get() = _binding!!

    private val usersViewModel by viewModel<UsersViewModel>()

    override fun getTheme(): Int = R.style.BaseBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = UserDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeUsers()
        usersViewModel.fetchUsers()
    }

    private fun observeUsers() {
        usersViewModel.users.observe(viewLifecycleOwner) { (status, data, error) ->
            when (status) {
                Resource.Status.LOADING -> {
                    binding.user.gone()
                    binding.usernameAddress.gone()
                    binding.user.gone()
                    binding.phoneWeb.gone()
                    binding.addresses.gone()
                    binding.company.gone()
                    binding.loader.visible()
                }
                Resource.Status.SUCCESS -> {
                    data?.let { mData ->
                        binding.loader.gone()
                        binding.user.visible()
                        binding.usernameAddress.visible()
                        binding.user.visible()
                        binding.phoneWeb.visible()
                        binding.addresses.visible()
                        binding.company.visible()

                        val user = mData.first()
                        binding.name.text = user.name
                        binding.userInitial.text = user.name.first().toString()

                        val username = "${user.username} - ${user.address.city}"
                        binding.usernameAddress.text = username

                        binding.email.text = user.email
                        binding.phone.text = user.email

                        with(user.address) {
                            val address = "${this.suite} - ${this.street}, ${this.city}"
                            binding.addresses.text = address
                        }

                        with(user.company) {
                            val company = "${this.name} (${this.catchPhrase}) offering ${this.bs}"
                            binding.company.text = company
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    binding.user.gone()
                    binding.usernameAddress.gone()
                    binding.user.gone()
                    binding.phoneWeb.gone()
                    binding.addresses.gone()
                    binding.company.gone()
                    binding.loader.visible()
                    error?.let { mError ->
                        Log.d(TAG, mError)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}