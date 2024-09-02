package com.bridge.androidtechnicaltest.ui.pupil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.adapter.PupilAdapter
import com.bridge.androidtechnicaltest.databinding.FragmentPupildetailBinding
import com.bridge.androidtechnicaltest.util.NetworkResource
import com.bridge.androidtechnicaltest.util.hideProgressDialog
import com.bridge.androidtechnicaltest.util.showProgressDialog
import com.bridge.androidtechnicaltest.viewmodel.PupilViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class PupilDetailFragment : Fragment(R.layout.fragment_pupildetail) {
    private lateinit var binding : FragmentPupildetailBinding
    private val pupilViewModel: PupilViewModel by viewModel()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPupildetailBinding.bind(view)




        binding.apply {
            btnGetPupil.setOnClickListener {
                val pupilId = binding.etPupilID.text.toString().takeIf { it.isNotEmpty() }?.toInt() ?: 0
                if (pupilId <= 0){
                    Toast.makeText(requireContext(), "Enter a valid ID number", Toast.LENGTH_SHORT).show()
                }else{
                    pupilViewModel.fetchPupilByID(binding.etPupilID.text.toString())
                    observeGetPupilData()
                }
            }
        }

        onBackPress()

    }


    private fun observeGetPupilData(){
        pupilViewModel.pupilLiveData.observe(viewLifecycleOwner){ result ->
            when (result) {
                is NetworkResource.Loading -> {
                    showProgressDialog(requireContext())
                }
                is NetworkResource.Success -> {
                    binding.layoutItems.visibility = View.VISIBLE
                    binding.layoutItemsTwo.visibility = View.VISIBLE
                    binding.layoutItemsThree.visibility = View.VISIBLE
                    showView(result.data.name, result.data.pupilId.toString(), result.data.latitude.toString(), result.data.longitude.toString())
                    hideProgressDialog(requireView())
                }
                is NetworkResource.Error -> {
                    hideProgressDialog(requireView())
                    Toast.makeText(requireContext(), result.throwable.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    fun showView(name: String, id: String, latitude: String, longitude: String){
        binding.pupilName.text = name
        binding.pupilId.text = id
        binding.lat.text = latitude
        binding.longitude.text = longitude
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