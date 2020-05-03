package com.rdt.orp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Bem Vindo ao ORP\nO app de comandas que vai mudar o seu negocio.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}