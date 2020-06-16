package com.example.clubtime.ui.listaalumnosdealumno;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class ListaAlumnosdeAlumnoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListaAlumnosdeAlumnoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
