package com.example.zakatmal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private RecyclerView historyRecyclerView;
    private HistoryAdapter historyAdapter;
    private ArrayList<String> historyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        historyRecyclerView = view.findViewById(R.id.history_recycler_view);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyList = new ArrayList<>();

        // Mengambil riwayat dari fragment sebelumnya
        if (getActivity() instanceof MainActivity) {
            JenisZakatFragment jenisZakatFragment = ((MainActivity) getActivity()).getJenisZakatFragment();
            if (jenisZakatFragment != null) {
                historyList.addAll(jenisZakatFragment.getHistoryList());
            }
        }

        historyAdapter = new HistoryAdapter(historyList);
        historyRecyclerView.setAdapter(historyAdapter);

        return view;
    }
}
