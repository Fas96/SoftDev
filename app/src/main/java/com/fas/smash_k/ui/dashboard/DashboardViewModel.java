package com.fas.smash_k.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mapbox.mapboxsdk.Mapbox;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<Mapbox> dmapView;


    public DashboardViewModel() {
        super();
        dmapView = new MutableLiveData<>();
    }
    public LiveData<Mapbox> getMap() {
    return dmapView;
     }
}