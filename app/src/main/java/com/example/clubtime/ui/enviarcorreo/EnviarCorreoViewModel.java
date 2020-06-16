package com.example.clubtime.ui.enviarcorreo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class EnviarCorreoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EnviarCorreoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
