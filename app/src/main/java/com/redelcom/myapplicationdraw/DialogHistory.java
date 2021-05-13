package com.redelcom.myapplicationdraw;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
//import androidx.fragment.app.DialogFragment;

public class DialogHistory extends DialogFragment {

    private ArrayList<Integer> valuesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_history, container, false);
        valuesList = this.getArguments().getIntegerArrayList("valueList");
        Toast.makeText(getActivity(), valuesList.toString(), Toast.LENGTH_SHORT).show();
        ArrayList<Integer> test = new ArrayList<>();
        test.add(1);
        test.add(6);
        test.add(5);
        test.add(4);
        ListAdapter listAdapter = new ListAdapter(test, this);
        RecyclerView recyclerView = view.findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
        return view;
    }
}
