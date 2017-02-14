package com.piotrkostecki.smarttravelpoznan.presentation.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piotrkostecki.smarttravelpoznan.domain.model.Direction;
import com.piotrkostecki.smarttravelpoznan.presentation.R;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DirectionAdapter extends RecyclerView.Adapter<DirectionAdapter.DirectionViewHolder> {

    private List<Direction> directionCollection;
    private final LayoutInflater layoutInflater;

    @Inject
    public DirectionAdapter(Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.directionCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.directionCollection != null) ? this.directionCollection.size() : 0;
    }

    @Override
    public DirectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_direction, parent, false);
        return new DirectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DirectionViewHolder holder, int position) {
        final Direction directionModel = this.directionCollection.get(position);
        holder.tv_lineName.setText(directionModel.getLineName());
        holder.tv_direction.setText(directionModel.getDirection());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setDirectionCollection(Collection<Direction> directionCollection) {
        this.validateStopCollection(directionCollection);
        this.directionCollection = (List<Direction>) directionCollection;
        this.notifyDataSetChanged();
    }

    private void validateStopCollection(Collection<Direction> directionCollection) {
        if (directionCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class DirectionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_line_name) TextView tv_lineName;
        @BindView(R.id.tv_direction) TextView tv_direction;

        public DirectionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}