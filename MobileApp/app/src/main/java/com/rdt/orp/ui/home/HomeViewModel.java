package com.rdt.orp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Seu cardápio está vazio...\nAdicione seus pratos para poder vê-los aqui!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}