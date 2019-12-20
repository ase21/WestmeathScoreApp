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

    @BindView(R.id.timeTextView)
    EditText timeTextView;

    @BindView(R.id.firstCommandNameEditText)
    EditText firstCommandNameEditText;
    @BindView(R.id.firstCommandsTotalPointsTextView)
    TextView firstCommandsTotalPointsTextView;
    @BindView(R.id.firstTeamGoalPlusButton)
    Button firstTeamGoalPlusButton;
    @BindView(R.id.firstTeamPointPlusButton)
    Button firstTeamPointPlusButton;

    @BindView(R.id.secondCommandNameEditText)
    EditText secondCommandNameEditText;
    @BindView(R.id.secondCommandsTotalPointsTextView)
    TextView secondCommandsTotalPointsTextView;
    @BindView(R.id.secondTeamGoalPlusButton)
    Button secondTeamGoalPlusButton;
    @BindView(R.id.secondTeamPointPlusButton)
    Button secondTeamPointPlusButton;

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

            if (elapsedMillis > time) {
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


    @OnClick({R.id.firstTeamGoalMinusButton, R.id.firstTeamGoalPlusButton, R.id.firstTeamPointPlusButton, R.id.firstTeamPointMinusButton})
    void onFirstTeamButtonsClicked(View view) {
        int firstTeamGoalsCount = Integer.parseInt(firstTeamGoalPlusButton.getText().toString());
        int firstTeamPointsCount = Integer.parseInt(firstTeamPointPlusButton.getText().toString());
        switch (view.getId()) {
            case R.id.firstTeamGoalMinusButton:
                firstTeamGoalsCount = firstTeamGoalsCount - 1;
                break;
            case R.id.firstTeamGoalPlusButton:
                firstTeamGoalsCount = firstTeamGoalsCount + 1;
                break;
            case R.id.firstTeamPointPlusButton:
                firstTeamPointsCount = firstTeamPointsCount + 1;
                break;
            case R.id.firstTeamPointMinusButton:
                firstTeamPointsCount = firstTeamPointsCount - 1;
                break;
        }
        int totalFirstTeamCount = firstTeamGoalsCount * 3 + firstTeamPointsCount;
        firstTeamGoalPlusButton.setText(firstTeamGoalsCount+"");
        firstTeamPointPlusButton.setText(firstTeamPointsCount+"");
        firstCommandsTotalPointsTextView.setText(totalFirstTeamCount + " pts");
    }


    @OnClick({R.id.secondTeamGoalMinusButton, R.id.secondTeamGoalPlusButton, R.id.secondTeamPointPlusButton, R.id.secondTeamPointMinusButton})
    void onSecondTeamButtonsClicked(View view) {
        int secondTeamGoalsCount = Integer.parseInt(secondTeamGoalPlusButton.getText().toString());
        int secondTeamPointsCount = Integer.parseInt(secondTeamPointPlusButton.getText().toString());
        switch (view.getId()) {
            case R.id.secondTeamGoalMinusButton:
                secondTeamGoalsCount = secondTeamGoalsCount - 1;
                break;
            case R.id.secondTeamGoalPlusButton:
                secondTeamGoalsCount = secondTeamGoalsCount + 1;
                break;
            case R.id.secondTeamPointPlusButton:
                secondTeamPointsCount = secondTeamPointsCount + 1;
                break;
            case R.id.secondTeamPointMinusButton:
                secondTeamPointsCount = secondTeamPointsCount - 1;
                break;
        }
        int totalSecondTeamCount = secondTeamGoalsCount * 3 + secondTeamPointsCount;
        secondTeamGoalPlusButton.setText(secondTeamGoalsCount+"");
        secondTeamPointPlusButton.setText(secondTeamPointsCount+"");
        secondCommandsTotalPointsTextView.setText(totalSecondTeamCount + " pts");
    }
}
