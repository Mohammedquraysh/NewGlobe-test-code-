package com.bridge.androidtechnicaltest.ui.pupil

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.adapter.PupilAdapter
import com.bridge.androidtechnicaltest.databinding.FragmentDeletePupilBinding
import com.bridge.androidtechnicaltest.util.NetworkResource
import com.bridge.androidtechnicaltest.util.hideProgressDialog
import com.bridge.androidtechnicaltest.util.showProgressDialog
import com.bridge.androidtechnicaltest.viewmodel.PupilViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class DeletePupilFragment : Fragment(R.layout.fragment_delete_pupil) {
    private lateinit var binding: FragmentDeletePupilBinding
    private val pupilViewModel: PupilViewModel by viewModel()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDeletePupilBinding.bind(view)
        binding.apply {

            btnDeletePupil.setOnClickListener {
                val pupilId = etDeleteID.text.toString().takeIf { it.isNotEmpty() }?.toInt() ?: 0
                if (pupilId <= 0){
                    Toast.makeText(requireContext(), "Enter a valid ID number", Toast.LENGTH_SHORT).show()
                }else{
                    pupilViewModel.deletePupilByID(pupilId)
                    observeDataToDelete()
                }
            }
        }

        onBackPress()
    }


    private fun observeDataToDelete(){
        pupilViewModel.operationResult.observe(viewLifecycleOwner){ result ->
            when (result) {
                is NetworkResource.Loading -> {
                   showProgressDialog(requireContext())
                }
                is NetworkResource.Success -> {
                    hideProgressDialog(requireView())

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