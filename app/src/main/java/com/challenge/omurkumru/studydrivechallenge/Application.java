package com.challenge.omurkumru.studydrivechallenge;

import android.content.Context;

import com.challenge.omurkumru.studydrivechallenge.di.AppComponent;
import com.challenge.omurkumru.studydrivechallenge.di.AppModule;
import com.challenge.omurkumru.studydrivechallenge.di.DaggerAppComponent;

public class Application extends android.app.Application {
    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
