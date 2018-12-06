package com.challenge.omurkumru.studydrivechallenge.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.challenge.omurkumru.studydrivechallenge.R;
import com.challenge.omurkumru.studydrivechallenge.model.DummyModel;

import java.util.Vector;

public class DummyListRecyclerViewAdapter extends RecyclerView.Adapter<DummyListRecyclerViewAdapter.ViewHolder> {

    private Vector<DummyModel> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    DummyListRecyclerViewAdapter(Context context, Vector<DummyModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_dummy, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Todo: solve inconsistency problem
        try {
            DummyModel model = mData.get(position);
            holder.titleTV.setText(model.getTitle());
            holder.detailTV.setText(model.getDetail());
            holder.authorTV.setText(model.getAuthor());
        } catch (Exception e) {
            Log.e("RVAdapter", "error occurred in adapter message: " + e.getLocalizedMessage());
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTV, detailTV, authorTV;

        ViewHolder(View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.title_TV);
            detailTV = itemView.findViewById(R.id.detail_TV);
            authorTV = itemView.findViewById(R.id.author_TV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
