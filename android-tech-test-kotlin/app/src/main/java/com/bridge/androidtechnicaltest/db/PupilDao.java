package com.bridge.androidtechnicaltest.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface PupilDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<Pupil> pupils);

    @Query("SELECT * FROM Pupils ORDER BY name ASC")
    Flowable<List<Pupil>> getPupils();
}
