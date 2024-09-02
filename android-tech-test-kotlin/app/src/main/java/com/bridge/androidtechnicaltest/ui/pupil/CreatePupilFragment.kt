package com.bridge.androidtechnicaltest.ui.pupil

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.adapter.PupilAdapter
import com.bridge.androidtechnicaltest.databinding.FragmentCreatePupilBinding
import com.bridge.androidtechnicaltest.db.model.create_pupil.CreatePupilModel
import com.bridge.androidtechnicaltest.util.NetworkResource
import com.bridge.androidtechnicaltest.util.hideProgressDialog
import com.bridge.androidtechnicaltest.util.showProgressDialog
import com.bridge.androidtechnicaltest.viewmodel.PupilViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class CreatePupilFragment : Fragment(R.layout.fragment_create_pupil) {
    private lateinit var binding : FragmentCreatePupilBinding
    private val pupilViewModel: PupilViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreatePupilBinding.bind(view)




        binding.btnCreateUser.setOnClickListener {
            val country = binding.etCountry.text.toString()
            val image = binding.etImageUrl.text.toString()
            val latitude = binding.etLatitude.text.toString().takeIf { it.isNotEmpty() }?.toDouble() ?: 0.0
            val longitude = binding.etLongitude.text.toString().takeIf { it.isNotEmpty() }?.toDouble() ?: 0.0
            val name = binding.etName.text.toString()
            val  pupil = CreatePupilModel(
                country = country,
                image = image,
                latitude =latitude,
                longitude = longitude,
                name = name
            )
            pupilViewModel.createPupil( pupil)
            observeCreatePupilData()
        }

        onBackPress()
    }


    private fun observeCreatePupilData(){
        pupilViewModel.pupilsLiveData.observe(viewLifecycleOwner){ result ->
            when (result) {
                is NetworkResource.Loading -> {
                    showProgressDialog(requireContext())
                }
                is NetworkResource.Success -> {
                    hideProgressDialog(requireView())
                    Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()


                }
                is NetworkResource.Error -> {
                    hideProgressDialog(requireView())
                    Toast.makeText(requireContext(), result.throwable.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun onBackPress(){
        val callBack = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity?.onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callBack)
    }


}