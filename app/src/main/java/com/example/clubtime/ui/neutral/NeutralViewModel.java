package com.example.clubtime.ui.neutral;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class NeutralViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NeutralViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
