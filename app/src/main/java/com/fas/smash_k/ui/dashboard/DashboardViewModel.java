package com.fas.smash_k.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mapbox.mapboxsdk.Mapbox;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<Mapbox> dmapView;
   // private Mapbox dmapbox;

//    public DashboardViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is dashboard fragment");
//    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }


    public DashboardViewModel() {
        super();
        dmapView = new MutableLiveData<>();
    }
    public LiveData<Mapbox> getMap() {
    return dmapView;
     }
}