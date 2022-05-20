package com.sample.httpssample;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    //get请求
    @GET("api/rand.music")
    Call<Data<Info>> getJsonData(@Query("sort") String sort, @Query("format") String format);

    @GET("api/rand.music")
    Observable<Data<Info>> getJsonDataObservable(@Query("sort") String sort, @Query("format") String format);
}