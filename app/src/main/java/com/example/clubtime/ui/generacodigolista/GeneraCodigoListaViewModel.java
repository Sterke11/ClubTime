package com.example.clubtime.ui.generacodigolista;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class GeneraCodigoListaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GeneraCodigoListaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
