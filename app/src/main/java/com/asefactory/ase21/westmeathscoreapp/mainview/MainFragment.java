package com.asefactory.ase21.westmeathscoreapp.mainview;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asefactory.ase21.westmeathscoreapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import moxy.MvpAppCompatFragment;

public class MainFragment extends MvpAppCompatFragment {

    @BindView(R.id.timeTextView)
    EditText timeTextView;
    private Unbinder unbinder;
    private boolean chronometerFlag = false;

    @BindView(R.id.chronometer)
    Chronometer chronometer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        unbinder = ButterKnife.bind(this, view);
        chronometer.setOnChronometerTickListener(chronometer -> {
            long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();

            long time = Long.parseLong(timeTextView.getText().toString()) * 1000 * 60;

            if (elapsedMillis > time){
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometerFlag = false;
            }
        });
    }

    @OnClick(R.id.startChronometerButton)
    void onStartChronometerClicked() {
        if (!chronometerFlag) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            chronometerFlag = true;
        }
    }
}
