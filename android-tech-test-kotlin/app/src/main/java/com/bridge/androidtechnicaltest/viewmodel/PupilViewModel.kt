package com.bridge.androidtechnicaltest.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bridge.androidtechnicaltest.db.IPupilRepository
import com.bridge.androidtechnicaltest.db.Pupil
import com.bridge.androidtechnicaltest.db.PupilList
import com.bridge.androidtechnicaltest.db.model.create_pupil.CreatePupilModel
import com.bridge.androidtechnicaltest.util.NetworkResource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PupilViewModel(private val repository: IPupilRepository) : ViewModel() {


    private val compositeDisposable = CompositeDisposable()



    private val _pupilsLiveData = MutableLiveData<NetworkResource<List<Pupil>>>()
    val pupilsLiveData: LiveData<NetworkResource<List<Pupil>>> = _pupilsLiveData

    private val _pupilLiveData = MutableLiveData<NetworkResource<Pupil>>()
    val pupilLiveData: LiveData<NetworkResource<Pupil>> = _pupilLiveData

    private val _operationResult = MutableLiveData<NetworkResource<Pupil>>()
    val operationResult: LiveData<NetworkResource<Pupil>> = _operationResult


    private val _pupilResult = MutableLiveData<NetworkResource<PupilList>>()
    val pupilResult: LiveData<NetworkResource<PupilList>> = _pupilResult

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }




    fun observePupilsFromDatabase() {
        compositeDisposable.add(
            repository.observePupilsFromDatabase()
                .subscribe { result ->
                    Log.e("MQ", "observePupilsFromDatabase: $result", )
                    _pupilsLiveData.postValue(result)
                }
        )


    }

    fun fetchPupilByID(pupilId: String) {
        compositeDisposable.add(
            repository.getPupilByID(pupilId)
                .subscribe { result ->
                    _pupilLiveData.postValue(result)
                }
        )


    }

    fun createPupil(pupil: CreatePupilModel) {
        compositeDisposable.add(
            repository.createPupil(pupil)
                .subscribe { result ->
                    _pupilLiveData.postValue(result)
                }
        )


    }

    fun deletePupilByID(pupilId: Int) {
        compositeDisposable.add(
            repository.deletePupilByID(pupilId)
                .subscribe { result ->
                    _operationResult.postValue(result)
                }
        )


    }

    fun updatePupilByID(pupilId: String, pupil: Pupil) {
        compositeDisposable.add(
            repository.updatePupilByID(pupilId, pupil)
                .subscribe { result ->
                    _pupilLiveData.postValue(result)
                }
        )

    }

    fun getOrFetchPupils() {
        compositeDisposable.add(
            repository.getOrFetchPupils()
                .subscribe { result ->
                    _pupilResult.postValue(result)
                }

        )

    }
}
