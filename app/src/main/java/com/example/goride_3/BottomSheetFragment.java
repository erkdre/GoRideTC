package com.example.goride_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private static final String ARG_SCOOTER_TITLE = "scooter_title";
    private String scooterTitle;

    // Neue Methode zum Erstellen einer Instanz des Fragments mit Argumenten
    public static BottomSheetFragment newInstance(String scooterTitle) {
        BottomSheetFragment fragment = new BottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SCOOTER_TITLE, scooterTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            scooterTitle = getArguments().getString(ARG_SCOOTER_TITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        Button startFahrtButton = view.findViewById(R.id.startFahrtButton);
        startFahrtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() != null) {
                    Intent intent = new Intent(getContext(), QRScannerActivity.class);
                    startActivity(intent);
                }
            }
        });

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        if (scooterTitle != null) {
            tvTitle.setText(scooterTitle);
        }

        return view;
    }
}