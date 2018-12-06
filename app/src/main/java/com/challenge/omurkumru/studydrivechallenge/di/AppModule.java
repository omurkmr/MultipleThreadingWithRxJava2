package com.challenge.omurkumru.studydrivechallenge.di;

import android.content.Context;

import com.challenge.omurkumru.studydrivechallenge.ui.main.MainRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    MainRepository provideEntranceRepository() {
        return new MainRepository();
    }
}