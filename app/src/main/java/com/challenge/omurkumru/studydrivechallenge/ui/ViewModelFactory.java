package com.challenge.omurkumru.studydrivechallenge.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.challenge.omurkumru.studydrivechallenge.ui.main.MainRepository;
import com.challenge.omurkumru.studydrivechallenge.ui.main.MainViewModel;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private MainRepository mMainRepository;
    private Context mContext;

    @Inject
    public ViewModelFactory(Context context,MainRepository mainRepository) {
        mMainRepository = mainRepository;
        mContext = context;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(mMainRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}