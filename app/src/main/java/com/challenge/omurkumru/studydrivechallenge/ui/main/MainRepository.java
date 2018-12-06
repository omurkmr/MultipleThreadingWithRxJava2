package com.challenge.omurkumru.studydrivechallenge.ui.main;

import android.arch.lifecycle.MutableLiveData;

import com.challenge.omurkumru.studydrivechallenge.model.DummyModel;

import java.util.Vector;


public class MainRepository {

    private MutableLiveData<Vector<DummyModel>> dummyTextList = new MutableLiveData<>();

    public MainRepository() {
        dummyTextList.setValue(new Vector<>());
    }

    public MutableLiveData<Vector<DummyModel>> getDummyTextList() {
        return dummyTextList;
    }
}