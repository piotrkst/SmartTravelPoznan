package com.piotrkostecki.smarttravelpoznan.presentation.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piotrkostecki.smarttravelpoznan.presentation.R;
import com.piotrkostecki.smarttravelpoznan.presentation.model.ArrivalModel;
import com.piotrkostecki.smarttravelpoznan.presentation.model.TimetableModel;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ArrivalAdapter extends RecyclerView.Adapter<ArrivalAdapter.TimetableViewHolder> {

    private Context context;
    private List<ArrivalModel> arrivalCollection;
    private final LayoutInflater layoutInflater;

    @Inject
    public ArrivalAdapter(Context context) {
        this.context = context;
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
        final ArrivalModel arrival = this.arrivalCollection.get(position);
        if (arrival.isRealTime()) {
            if (arrival.getMinutes() == 0) {
                holder.tv_departure.setText(context.getResources().getString(R.string.tv_departure_real_time_lt));
            } else {
                holder.tv_departure.setText(context.getResources().getString(R.string.tv_departure_real_time, String.valueOf(arrival.getMinutes())));
            }
            holder.tv_gps.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.tv_departure.setTextColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            holder.tv_gps.setTextColor(Color.TRANSPARENT);
            holder.tv_departure.setTextColor(Color.BLACK);
            holder.tv_departure.setText(context.getResources().getString(R.string.tv_departure_agenda_time, arrival.getDeparture().substring(11, 16)));
        }
        holder.tv_direction.setText(String.valueOf(arrival.getDirection()));
        holder.tv_line.setText(arrival.getLine());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setTimetableCollection(TimetableModel timetables) {
        this.validateTimetableCollection(timetables);
        this.arrivalCollection = (List<ArrivalModel>) timetables.getArrivals();
        this.notifyDataSetChanged();
    }

    private void validateTimetableCollection(TimetableModel arrivalCollection) {
        if (arrivalCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class TimetableViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_departure) TextView tv_departure;
        @BindView(R.id.tv_direction) TextView tv_direction;
        @BindView(R.id.tv_line) TextView tv_line;
        @BindView(R.id.tv_gps) TextView tv_gps;

        public TimetableViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
