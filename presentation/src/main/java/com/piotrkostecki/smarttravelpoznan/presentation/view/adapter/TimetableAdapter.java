package com.piotrkostecki.smarttravelpoznan.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.piotrkostecki.smarttravelpoznan.presentation.R;
import com.piotrkostecki.smarttravelpoznan.presentation.model.DirectionModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;


public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.TimetableViewHolder> {

    public interface OnItemClickListener {
        void onTimetableItemClicked(DirectionModel directionModel);
    }

    private List<DirectionModel> timetableCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    @Inject
    public TimetableAdapter(Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.timetableCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.timetableCollection != null) ? this.timetableCollection.size() : 0;
    }

    @Override
    public TimetableAdapter.TimetableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_timetable, parent, false);
        return new TimetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimetableViewHolder holder, int position) {
        final DirectionModel directionModel = this.timetableCollection.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setTimetableCollection(Collection<DirectionModel> timetableCollection) {
        this.validateTimetableCollection(timetableCollection);
        this.timetableCollection = (List<DirectionModel>) timetableCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateTimetableCollection(Collection<DirectionModel> timetableCollection) {
        if (timetableCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class TimetableViewHolder extends RecyclerView.ViewHolder {

        public TimetableViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
