package com.example.cp.chauffeurp.ui.home;

import android.content.Context;

import com.example.cp.chauffeurp.data.ApiServiceManager;
import com.example.cp.chauffeurp.data.model.Address;
import com.example.cp.chauffeurp.data.model.ReversePosition;
import com.example.cp.chauffeurp.ui.base.mvp.BasePresenter;

import java.util.UUID;

import javax.inject.Inject;

import io.realm.Realm;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * Created by fanilogabaud on 06/01/2018.
 */

public class HomePresenter extends BasePresenter<HomeView> {

    @Inject
    ApiServiceManager apiServiceManager;
    @Inject
    Realm realm;

    @Inject
    public HomePresenter(Context context) {
    }

    @Override
    public void initialize() {

    }

    void retrievePlaceNameFromPosition(Double longitude, Double latitude) {
        apiServiceManager.getPlaceNameFromPostion(longitude, latitude)
                .subscribe(new Action1<ReversePosition>() {
                    @Override
                    public void call(ReversePosition reversePosition) {
                        String placeName = reversePosition.getFeatures().get(0).getPlaceName();
                        Timber.d("Place name " + placeName);
                        if (view != null) {
                            view.onPlaceNameFound(placeName);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.e(throwable, "error while checking for place name");
                    }
                });
    }

    void cacheSearch(String placeName) {
        Address address = new Address();
        address.setId(UUID.randomUUID().getMostSignificantBits());
        address.setSearchField(placeName);
        address.setTimeStamp(System.currentTimeMillis());
        realm.executeTransactionAsync(bgRealm -> bgRealm.copyToRealmOrUpdate(address));
    }
}
