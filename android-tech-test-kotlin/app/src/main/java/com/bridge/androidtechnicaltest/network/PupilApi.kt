package com.bridge.androidtechnicaltest.network

import com.bridge.androidtechnicaltest.db.Pupil
import com.bridge.androidtechnicaltest.db.PupilList
import com.bridge.androidtechnicaltest.db.model.create_pupil.CreatePupilModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PupilApi {
    @GET("pupils")
    fun getPupils(@Query("page") page: Int = 1): Observable<PupilList>

    @GET("pupils/{pupilId}")
    fun getPupilById(
        @Path("pupilId") pupilId : String
    ): Observable<Pupil>

    @PUT("pupils/{pupilId}")
    fun updatePupilById(
        @Path("pupilId") pupilId : String,
        @Body pupil: Pupil
    ): Observable<Pupil>

    @POST("pupils")
    fun createPupil(
        @Body createPupil: CreatePupilModel
    ): Observable<Pupil>


    @DELETE("pupils/{pupilId}")
    fun deletePupilById(
        @Path("pupilId") pupilId : Int,
    ): Observable<Pupil>
}