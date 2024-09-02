package com.bridge.androidtechnicaltest.ui.pupil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.adapter.PupilAdapter
import com.bridge.androidtechnicaltest.databinding.FragmentPupillistBinding
import com.bridge.androidtechnicaltest.util.NetworkResource
import com.bridge.androidtechnicaltest.util.hideProgressDialog
import com.bridge.androidtechnicaltest.util.showProgressDialog
import com.bridge.androidtechnicaltest.viewmodel.PupilViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class PupilListFragment : Fragment(R.layout.fragment_pupillist) {
    private lateinit var binding : FragmentPupillistBinding
    private val pupilViewModel: PupilViewModel by viewModel()
    private lateinit var adapter: PupilAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pupillist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPupillistBinding.bind(view)



        initRecyclerView()
        pupilViewModel.getOrFetchPupils()
        pupilViewModel.observePupilsFromDatabase()
        observeData()
        fetchPupilFromData()
        onBackPress()

    }

    private fun initRecyclerView(){
        adapter = PupilAdapter(emptyList())
        binding.pupilList.layoutManager = LinearLayoutManager(context)
        binding.pupilList.adapter = adapter

    }


    private fun observeData(){
        pupilViewModel.pupilsLiveData.observe(viewLifecycleOwner){ result ->
            when (result) {
                is NetworkResource.Loading -> {
                    showProgressDialog(requireContext())
                }
                is NetworkResource.Success -> {
                    adapter = PupilAdapter(result.data)
                    binding.pupilList.adapter = adapter
                    hideProgressDialog(requireView())
                }
                is NetworkResource.Error -> {
                    hideProgressDialog(requireView())
                    Toast.makeText(requireContext(), result.throwable.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun fetchPupilFromData(){
        pupilViewModel.pupilResult.observe(viewLifecycleOwner){ result ->
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
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callBack)
    }



}