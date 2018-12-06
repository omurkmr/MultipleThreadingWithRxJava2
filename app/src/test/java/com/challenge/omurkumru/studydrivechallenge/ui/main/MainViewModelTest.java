package com.challenge.omurkumru.studydrivechallenge.ui.main;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.challenge.omurkumru.studydrivechallenge.model.DummyModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;

@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

    private MainViewModel mainViewModel;

    @Mock
    private MainRepository repository;

    private TestScheduler testScheduler;

    private static Scheduler immediate;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void setDatas(){
        MutableLiveData<Vector<DummyModel>> testData = new MutableLiveData<>();
        Vector<DummyModel> list = new Vector<>();
        list.add(new DummyModel("first","detail1","test"));
        testData.setValue(list);

        DummyModel testModel = new DummyModel("second","detail2","test2");
    }

    @BeforeClass
    public static void setUpRxSchedulers() {
        immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }


    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        mainViewModel = Mockito.spy(new MainViewModel(repository));
    }

    @Test
    public void getDummyModelTest() {

        DummyModel testData = new DummyModel("Nice Title","Nice article too","Ömür Kumru");

        Assert.assertEquals(testData.getTitle(),mainViewModel.getDummyModel().getTitle());
        Assert.assertEquals(testData.getDetail(),mainViewModel.getDummyModel().getDetail());
        Assert.assertEquals(testData.getAuthor(),mainViewModel.getDummyModel().getAuthor());
    }

    //todo: add rxjava2 tests
}
