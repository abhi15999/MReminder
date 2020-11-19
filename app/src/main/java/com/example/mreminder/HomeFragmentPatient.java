package com.example.mreminder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class HomeFragmentPatient extends Fragment{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> list;
    private MedicineRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_patient, container, false);

//        recyclerView = view.findViewById(R.id.recyclerview_home);
//        // layout manager, view holder, adapter
//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//
//        list = Arrays.asList(getResources().getStringArray(R.array.Medicines));
//        adapter = new MedicineRecyclerAdapter(list);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(adapter);
        return view;
    }
}
