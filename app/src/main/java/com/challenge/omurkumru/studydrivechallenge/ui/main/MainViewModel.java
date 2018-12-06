package com.challenge.omurkumru.studydrivechallenge.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.challenge.omurkumru.studydrivechallenge.model.DummyModel;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private MainRepository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public MainViewModel(MainRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<Vector<DummyModel>> getList() {
        return repository.getDummyTextList();
    }

    void addProducer(){
        disposables.add(Observable.just(getList())
                //lets add new value to our list
                .doOnNext(s -> s.getValue().add(getDummyModel()))
                //we need 3 sec delay,also have a infinite task so we need new thread
                //also do again and and and again
                .repeatWhen(observable -> observable.delay(3000, TimeUnit.MILLISECONDS,Schedulers.newThread()))
                //have to handle error
                .doOnError(throwable -> Log.e(TAG,"Error occurred on producer, error message : " + throwable.getMessage()))
                //MutableLiveData's setValue method can callable only in mainThread
                .observeOn(AndroidSchedulers.mainThread())
                //to trigger livedata's listener we have to call setValue method
                //unfortunately if we are updating a list then have to call setValue method to trigger listener
                .subscribe(s -> s.setValue(s.getValue())
                ));
    }

    void addConsumer(){
        disposables.add(Observable.just(getList())
                //we need 4 sec delay,also have a infinite task so we need new thread
                //also do again and and and again
                .repeatWhen(observable -> observable.delay(4000, TimeUnit.MILLISECONDS,Schedulers.newThread()))
                //lets remove last item from list
                .doOnNext(s -> {
                    if (s.getValue()!= null && !s.getValue().isEmpty())
                    s.getValue().remove(s.getValue().size()-1);
                })
                //MutableLiveData's setValue method can callable only in mainThread
                .observeOn(AndroidSchedulers.mainThread())
                //have to handle error
                .doOnError(throwable -> Log.e(TAG,"Error occurred on consumer, error message : " + throwable.getMessage()))
                //to trigger livedata's listener we have to call setValue method
                //unfortunately if we are updating a list then have to call setValue method to trigger listener
                .subscribe(s -> s.setValue(s.getValue())
                ));
    }

    //just create and return fake model
    DummyModel getDummyModel(){
        return new DummyModel("Nice Title","Nice article too","Ömür Kumru");
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

}
