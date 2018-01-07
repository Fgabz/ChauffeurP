package com.example.cp.chauffeurp.data;

import com.example.cp.chauffeurp.data.model.ReversePosition;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by fanilogabaud on 06/01/2018.
 */

public interface IApiService {

    @GET("geocoding/v5/mapbox.places/{long},{lat}.json")
    Observable<ReversePosition> getPlaceNameFromPostion(@Path("long") Double longitude,
                                                        @Path("lat") Double latitude);
}
