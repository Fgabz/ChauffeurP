package com.example.cp.chauffeurp.data;

import com.example.cp.chauffeurp.data.model.ReversePosition;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fanilogabaud on 06/01/2018.
 */

public class ApiServiceManager {
    private IApiService iApiService;

    public ApiServiceManager(IApiService iApiService) {
        this.iApiService = iApiService;
    }

    public Observable<ReversePosition> getPlaceNameFromPostion(Double longitude, Double latitude) {
        return iApiService.getPlaceNameFromPostion(longitude, latitude)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

    }
}
