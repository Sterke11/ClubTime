package com.example.clubtime.ui.ingresarcodigo;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class IngresarCodigoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public IngresarCodigoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
