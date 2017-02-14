package com.piotrkostecki.smarttravelpoznan.presentation.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piotrkostecki.smarttravelpoznan.presentation.R;
import com.piotrkostecki.smarttravelpoznan.presentation.model.StopModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StopAdapter extends RecyclerView.Adapter<StopAdapter.StopViewHolder> {

    public interface OnItemClickListener {
        void onStopNameItemClicked(StopModel stopModel);
    }

    private List<StopModel> stopCollection;
    private final LayoutInflater layoutInflater;

    private StopAdapter.OnItemClickListener onItemClickListener;

    @Inject
    public StopAdapter(Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.stopCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.stopCollection != null) ? this.stopCollection.size() : 0;
    }

    @Override
    public StopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_stop, parent, false);
        return new StopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StopViewHolder holder, int position) {
        final StopModel stopModel = this.stopCollection.get(position);
        holder.tv_stop.setText(stopModel.getName());
        holder.tv_stop.setOnClickListener(click -> StopAdapter.this.onItemClickListener.onStopNameItemClicked(stopModel));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setStopCollection(Collection<StopModel> stopCollection) {
        this.validateStopCollection(stopCollection);
        this.stopCollection = (List<StopModel>) stopCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener (StopAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateStopCollection(Collection<StopModel> stopCollection) {
        if (stopCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class StopViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_stop) TextView tv_stop;

        public StopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
