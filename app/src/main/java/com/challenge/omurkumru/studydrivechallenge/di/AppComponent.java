package com.challenge.omurkumru.studydrivechallenge.di;

import com.challenge.omurkumru.studydrivechallenge.ui.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    void inject(MainActivity activity);

}
