package com.piotrkostecki.smarttravelpoznan.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piotrkostecki.smarttravelpoznan.domain.model.Arrival;
import com.piotrkostecki.smarttravelpoznan.domain.model.Timetable;
import com.piotrkostecki.smarttravelpoznan.presentation.R;
import com.piotrkostecki.smarttravelpoznan.presentation.model.ArrivalModel;
import com.piotrkostecki.smarttravelpoznan.presentation.model.StopModel;
import com.piotrkostecki.smarttravelpoznan.presentation.model.TimetableModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.TimetableViewHolder> {

    private List<Arrival> arrivalCollection;
    private TimetableModel timetables;
    private final LayoutInflater layoutInflater;

    @Inject
    public TimetableAdapter(Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrivalCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.arrivalCollection != null) ? this.arrivalCollection.size() : 0;
    }

    @Override
    public TimetableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_timetable, parent, false);
        return new TimetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimetableViewHolder holder, int position) {
        final Arrival arrival = this.arrivalCollection.get(position);
        holder.tv_departure.setText(String.valueOf(arrival.getMinutes()));
        holder.tv_line.setText(arrival.getLine());

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setTimetableCollection(TimetableModel timetables) {
        this.validateTimetableCollection(timetables);
        this.arrivalCollection = timetables.getArrivals();
        this.notifyDataSetChanged();
    }

    private void validateTimetableCollection(TimetableModel arrivalCollection) {
        if (arrivalCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class TimetableViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_departure) TextView tv_departure;
        @BindView(R.id.tv_line) TextView tv_line;

        public TimetableViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
