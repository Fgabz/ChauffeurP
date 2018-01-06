package com.example.cp.chauffeurp.data;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by fanilogabaud on 06/01/2018.
 */

public interface IApiService {

    @GET("v3/users/{user_id}.json")
    Observable<Object> getPlaceNameFromPostion(@Path("user_id") int userId);
}
