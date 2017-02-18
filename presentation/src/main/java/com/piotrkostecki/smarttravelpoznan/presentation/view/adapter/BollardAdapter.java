package com.piotrkostecki.smarttravelpoznan.presentation.view.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piotrkostecki.smarttravelpoznan.presentation.R;
import com.piotrkostecki.smarttravelpoznan.presentation.model.BollardModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BollardAdapter extends RecyclerView.Adapter<BollardAdapter.BollardViewHolder> {

    public interface OnItemClickListener {
        void onBollardItemClicked(BollardModel bollardModel);
    }

    private List<BollardModel> bollardCollection;
    private final LayoutInflater layoutInflater;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public BollardAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.bollardCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.bollardCollection != null) ? this.bollardCollection.size() : 0;
    }

    @Override
    public BollardAdapter.BollardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_bollard, parent, false);
        return new BollardAdapter.BollardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BollardAdapter.BollardViewHolder holder, int position) {
        final BollardModel bollardModel = this.bollardCollection.get(position);
        holder.cv_container.setOnClickListener(click -> BollardAdapter.this.onItemClickListener.onBollardItemClicked(bollardModel));
        holder.tv_bollard.setText(bollardModel.getBollardInfo().getTag());
        DirectionAdapter directionAdapter = new DirectionAdapter(
                context, () -> BollardAdapter.this.onItemClickListener.onBollardItemClicked(bollardModel));
        directionAdapter.setDirectionCollection(bollardModel.getDirections());
        holder.rv_directions.setLayoutManager(new CustomLayoutManager(context));
        holder.rv_directions.setAdapter(directionAdapter);
        directionAdapter.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setBollardCollection(Collection<BollardModel> bollardCollection) {
        this.validateDirectionCollection(bollardCollection);
        this.bollardCollection = (List<BollardModel>) bollardCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(BollardAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateDirectionCollection(Collection<BollardModel> directionCollection) {
        if (directionCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class BollardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cv_container) CardView cv_container;
        @BindView(R.id.tv_bollard) TextView tv_bollard;
        @BindView(R.id.rv_directions) RecyclerView rv_directions;

        public BollardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}