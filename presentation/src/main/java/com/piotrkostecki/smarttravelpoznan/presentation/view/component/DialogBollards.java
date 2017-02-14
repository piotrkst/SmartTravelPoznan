package com.piotrkostecki.smarttravelpoznan.presentation.view.component;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.piotrkostecki.smarttravelpoznan.presentation.R;
import com.piotrkostecki.smarttravelpoznan.presentation.model.BollardModel;
import com.piotrkostecki.smarttravelpoznan.presentation.view.adapter.BollardAdapter;
import com.piotrkostecki.smarttravelpoznan.presentation.view.adapter.CustomLayoutManager;

import java.util.Collection;


import butterknife.BindView;
import butterknife.ButterKnife;


public class DialogBollards extends DialogFragment {

    private BollardAdapter bollardAdapter;

    @BindView(R.id.rv_bollards) RecyclerView rv_bollards;

    public Context context;
    private Collection<BollardModel> bollardModelCollection;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_bollards, null);
        ButterKnife.bind(this, view);
        initializeAdapter();
        setupRecyclerView();
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.bollardAdapter = new BollardAdapter(context);
        this.bollardAdapter.setOnItemClickListener((BollardAdapter.OnItemClickListener) context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rv_bollards.setAdapter(null);
    }

    private void setupRecyclerView() {
        this.rv_bollards.setLayoutManager(new CustomLayoutManager(context));
        this.rv_bollards.setAdapter(bollardAdapter);
    }

    public void setBollardCollection(Collection<BollardModel> bollardModelCollection) {
        this.bollardModelCollection = bollardModelCollection;

    }

    private void initializeAdapter() {
        this.bollardAdapter.setBollardCollection(bollardModelCollection);
    }
}