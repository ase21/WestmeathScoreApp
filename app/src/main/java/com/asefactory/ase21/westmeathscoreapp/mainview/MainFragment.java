package com.asefactory.ase21.westmeathscoreapp.mainview;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asefactory.ase21.westmeathscoreapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import moxy.MvpAppCompatFragment;

public class MainFragment extends MvpAppCompatFragment {

    @BindView(R.id.chronometer)
    Chronometer chronometer;
    @BindView(R.id.timeTextView)
    EditText timeTextView;
    @BindView(R.id.startChronometerButton)
    Button startChronometerButton;
    @BindView(R.id.halfNameTextView)
    TextView halfNameTextView;

    private Unbinder unbinder;
    private boolean chronometerStarted = false;
    private boolean isFirstHalfIsFinished = false;

    private long firstHalfTime;
    private long secondHalfTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @OnClick(R.id.startChronometerButton)
    void onStartChronometerClicked() {
        checkValidTextFields();
        initChronometer();
    }

    private void checkValidTextFields() {
        firstHalfTime = Long.parseLong(timeTextView.getText().toString()) * 1000 * 60;
        secondHalfTime = Long.parseLong(timeTextView.getText().toString()) * 1000 * 60 * 2;
    }

    private void initChronometer() {
            if(!isFirstHalfIsFinished) {
                if (!chronometerStarted) {
                    prepareChronometer(SystemClock.elapsedRealtime(), firstHalfTime);
                    startChronometerButton.setText(getResources().getText(R.string.half_time));
                    halfNameTextView.setText(getResources().getText(R.string.first_half));
                } else {
                    chronometer.stop();
                    chronometer.setTextColor(getResources().getColor(android.R.color.black));
                    startChronometerButton.setText(getResources().getText(R.string.second_half));
                    isFirstHalfIsFinished = true;
                    chronometerStarted = false;
                }
            } else {
                if (!chronometerStarted) {
                    prepareChronometer(SystemClock.elapsedRealtime() - firstHalfTime, secondHalfTime);
                    startChronometerButton.setText(getResources().getText(R.string.full_time));
                    halfNameTextView.setText(getResources().getText(R.string.second_half));
                } else {
                    chronometer.stop();
                    chronometer.setTextColor(getResources().getColor(android.R.color.black));
                    startChronometerButton.setText(getResources().getText(R.string.second_half));
                    isFirstHalfIsFinished = true;
                    chronometerStarted = false;
                }
            }

    }

    private void prepareChronometer(long l, long firstHalfTime) {
        setChronometerTime(l);
        setChronometerClickListiner(firstHalfTime);
        chronometer.start();
        chronometerStarted = true;
    }

    private void setChronometerTime(long time) {
        chronometer.setBase(time);
    }

    private void setChronometerClickListiner(long firstHalfTime) {
        chronometer.setOnChronometerTickListener(chronometer -> {
            long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();

            if (elapsedMillis > firstHalfTime) {
                chronometer.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });
    }
}