package com.bridge.androidtechnicaltest.db

import android.util.Log
import com.bridge.androidtechnicaltest.db.model.create_pupil.CreatePupilModel
import com.bridge.androidtechnicaltest.network.PupilApi
import com.bridge.androidtechnicaltest.util.NetworkResource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


interface IPupilRepository {
//    fun getOrFetchPupils() : Completable
//    fun observePupilsFromDatabase(): Flowable<List<Pupil>>
//
//    fun getPupilByID(pupilId: String): Single<Pupil>
//
//    fun updatePupilByID(pupilId : String,pupil: Pupil): Single<Pupil>
//
//    fun deletePupilByID(pupilId : Int): Single<Pupil>
//
//    fun createPupil(pupil: CreatePupilModel): Single<Pupil>


    fun getOrFetchPupils(): Observable<NetworkResource<PupilList>>
    fun observePupilsFromDatabase(): Observable<NetworkResource<List<Pupil>>>

    fun getPupilByID(pupilId: String): Observable<NetworkResource<Pupil>>

    fun updatePupilByID(pupilId: String, pupil: Pupil): Observable<NetworkResource<Pupil>>

    fun deletePupilByID(pupilId: Int): Observable<NetworkResource<Pupil>>

    fun createPupil(pupil: CreatePupilModel): Observable<NetworkResource<Pupil>>

}
class PupilRepository(private val pupilDao: PupilDao,private val pupilApi: PupilApi)
    : IPupilRepository {



    override fun getOrFetchPupils(): Observable<NetworkResource<PupilList>> {
        return pupilApi.getPupils()
            .flatMapCompletable { pupils ->
                pupilDao.insertAll(pupils.items)
            }
            .toObservable<PupilList>()
            .map<NetworkResource<PupilList>> {pupils ->
                NetworkResource.Success(pupils)
            }
            .onErrorReturn { throwable ->
                NetworkResource.Error(throwable) }
            .startWith(NetworkResource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    override fun observePupilsFromDatabase(): Observable<NetworkResource<List<Pupil>>> {
        return pupilDao.getPupils()
            .map<NetworkResource<List<Pupil>>> { pupils ->
                NetworkResource.Success(pupils) }
            .onErrorReturn { throwable -> NetworkResource.Error(throwable) }
            .toObservable()
            .startWith(NetworkResource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getPupilByID(pupilId: String): Observable<NetworkResource<Pupil>> {
        return pupilApi.getPupilById(pupilId)
            .map<NetworkResource<Pupil>> { pupil -> NetworkResource.Success(pupil) }
            .onErrorReturn { throwable -> NetworkResource.Error(throwable) }
            .startWith(NetworkResource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updatePupilByID(pupilId: String, pupil: Pupil): Observable<NetworkResource<Pupil>> {
        return pupilApi.updatePupilById(pupilId, pupil)
            .map<NetworkResource<Pupil>> { updatedPupil -> NetworkResource.Success(updatedPupil) }
            .onErrorReturn { throwable -> NetworkResource.Error(throwable) }
            .startWith(NetworkResource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deletePupilByID(pupilId: Int): Observable<NetworkResource<Pupil>> {
        return pupilApi.deletePupilById(pupilId)
            .map<NetworkResource<Pupil>> { pupil-> NetworkResource.Success(pupil) }
            .onErrorReturn { throwable -> NetworkResource.Error(throwable) }
            .startWith(NetworkResource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun createPupil(pupil: CreatePupilModel): Observable<NetworkResource<Pupil>> {
        return pupilApi.createPupil(pupil)
            .map<NetworkResource<Pupil>> { newPupil -> NetworkResource.Success(newPupil) }
            .onErrorReturn { throwable -> NetworkResource.Error(throwable) }
            .startWith(NetworkResource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}