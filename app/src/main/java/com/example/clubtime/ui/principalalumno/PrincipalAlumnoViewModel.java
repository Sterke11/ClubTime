package com.example.clubtime.ui.principalalumno;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class PrincipalAlumnoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PrincipalAlumnoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
