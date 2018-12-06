package com.challenge.omurkumru.studydrivechallenge.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.challenge.omurkumru.studydrivechallenge.Application;
import com.challenge.omurkumru.studydrivechallenge.R;
import com.challenge.omurkumru.studydrivechallenge.di.AppComponent;
import com.challenge.omurkumru.studydrivechallenge.model.DummyModel;
import com.challenge.omurkumru.studydrivechallenge.ui.ViewModelFactory;

import java.util.Vector;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    MainViewModel viewModel;

    DummyListRecyclerViewAdapter adapter;
    @BindView(R.id.list_RV)
    RecyclerView recyclerView;

    @Inject
    ViewModelFactory mViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Inject the current activity inside Dagger 2 dependency graph
        AppComponent applicationComponent = ((Application) getApplication()).getAppComponent();
        applicationComponent.inject(this);

        viewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);

        viewModel.getList().observe(this, this::listUpdateListener);

        // set up the RecyclerView
        //Todo: note to myself. databinding can be used here. Refactor when have time
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DummyListRecyclerViewAdapter(this, viewModel.getList().getValue());
        recyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);

    }

    private void listUpdateListener(Vector<DummyModel> dummyModelList) {
        //if we dont use this and use too many threads then it causes ''Inconsistency detected''
        //probably not the fastest solution but it prevents crash(at least mostly)

        recyclerView.getRecycledViewPool().clear();
        recyclerView.stopScroll();
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.addProducer_Button)
    public void addProducerClicked() {
        viewModel.addProducer();
    }

    @OnClick(R.id.addConsumer_Button)
    public void addConsumerClicked() {
        viewModel.addConsumer();
    }
}
