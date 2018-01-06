package com.example.cp.chauffeurp.data;

import java.util.Observable;

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

    public Observable<Object> getPlaceNameFromPostion(String lat, String lng) {
        return iApiService.getPlaceNameFromPostion(lat, lng)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

    }
}
