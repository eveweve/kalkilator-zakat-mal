package com.example.zakatmal;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class JenisZakatFragment extends Fragment {

    // List untuk menyimpan riwayat zakat
    private final ArrayList<String> historyList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jenis_zakat, container, false);

        Button buttonEmas = view.findViewById(R.id.button_emis);
        Button buttonBeras = view.findViewById(R.id.button_beras);
        Button buttonUang = view.findViewById(R.id.button_uang);
        Button buttonRiwayat = view.findViewById(R.id.button_riwayat);

        buttonEmas.setOnClickListener(v -> showInputDialog("emas"));
        buttonBeras.setOnClickListener(v -> showInputDialog("beras"));
        buttonUang.setOnClickListener(v -> showInputDialog("uang"));
        buttonRiwayat.setOnClickListener(v -> showHistoryDialog());

        return view;
    }

    private void showInputDialog(String jenisZakat) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Input " + jenisZakat);

        final EditText input = new EditText(getActivity());
        builder.setView(input);

        builder.setPositiveButton("Hitung", (dialog, which) -> {
            String inputText = input.getText().toString();
            if (!inputText.isEmpty()) {
                double nominal = Double.parseDouble(inputText);
                calculateZakat(jenisZakat, nominal);
            } else {
                Toast.makeText(getActivity(), "Masukkan nilai yang valid", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Batal", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void calculateZakat(String jenisZakat, double nominal) {
        double zakat = 0.0;

        // Konversi dan batas minimal
        switch (jenisZakat) {
            case "emas":
                // Asumsi 1 gram emas = 1000 IDR (ganti sesuai nilai pasar)
                if (nominal >= 0.25) { // Minimum 250 gram
                    zakat = nominal * 1000 * 0.025;
                } else {
                    Toast.makeText(getActivity(), "Minimum emas 250 gram", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case "beras":
                // Asumsi 1 kg beras = 10000 IDR (ganti sesuai nilai pasar)
                if (nominal >= 1) { // Minimum 1 kg
                    zakat = nominal * 10000 * 0.025;
                } else {
                    Toast.makeText(getActivity(), "Minimum beras 1 kg", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case "uang":
                // Minimum 100000 IDR
                if (nominal >= 100000) {
                    zakat = nominal * 0.025;
                } else {
                    Toast.makeText(getActivity(), "Minimum uang 100000 IDR", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }

        // Tampilkan hasil dan simpan ke riwayat
        String result = "Zakat " + jenisZakat + ": " + zakat + " IDR";
        showPopup(result); // Tampilkan hasil di popup
        historyList.add(result);
        // Simpan hasil ke riwayat
    }

    private void showPopup(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showHistoryDialog() {
        StringBuilder history = new StringBuilder();
        for (String item : historyList) {
            history.append(item).append("\n");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Riwayat Zakat")
                .setMessage(history.length() == 0 ? "Tidak ada riwayat zakat." : history.toString())
                .setPositiveButton("OK", (dialog, id) -> dialog.dismiss())
                .create()
                .show();
    }

    // Getter untuk riwayat zakat
    public ArrayList<String> getHistoryList() {
        return historyList;
    }
}
