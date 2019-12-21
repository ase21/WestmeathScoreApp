package com.asefactory.ase21.westmeathscoreapp.mainview;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.asefactory.ase21.westmeathscoreapp.MainActivity;
import com.asefactory.ase21.westmeathscoreapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment extends Fragment {

    @BindView(R.id.chronometer)
    Chronometer chronometer;
    @BindView(R.id.timeTextView)
    EditText timeTextView;
    @BindView(R.id.startChronometerButton)
    Button startChronometerButton;
    @BindView(R.id.halfNameTextView)
    TextView halfNameTextView;

    @BindView(R.id.firstCommandsTotalPointsTextView)
    TextView firstCommandsTotalPointsTextView;
    @BindView(R.id.firstTeamGoalPlusButton)
    Button firstTeamGoalPlusButton;
    @BindView(R.id.firstTeamPointPlusButton)
    Button firstTeamPointPlusButton;

    @BindView(R.id.secondCommandsTotalPointsTextView)
    TextView secondCommandsTotalPointsTextView;
    @BindView(R.id.secondTeamGoalPlusButton)
    Button secondTeamGoalPlusButton;
    @BindView(R.id.secondTeamPointPlusButton)
    Button secondTeamPointPlusButton;
    @BindView(R.id.socialTitleEditText)
    EditText socialTitleEditText;
    @BindView(R.id.tweetInformationEditText)
    EditText tweetInformationEditText;
    @BindView(R.id.firstCommandNameEditText)
    EditText firstCommandNameEditText;
    @BindView(R.id.secondCommandNameEditText)
    EditText secondCommandNameEditText;
    @BindView(R.id.tagEditText)
    EditText tagEditText;

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
        if(timeTextView.getText().toString().isEmpty()){
            timeTextView.setError("Field is empty");
        } else {
            checkValidTextFields();
            initChronometer();
        }
    }

    private void checkValidTextFields() {
        firstHalfTime = Long.parseLong(timeTextView.getText().toString()) * 1000 * 60;
        secondHalfTime = Long.parseLong(timeTextView.getText().toString()) * 1000 * 60 * 2;
    }

    private void initChronometer() {
        if (!isFirstHalfIsFinished) {
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


    @OnClick({R.id.firstTeamGoalMinusButton, R.id.firstTeamGoalPlusButton, R.id.firstTeamPointPlusButton, R.id.firstTeamPointMinusButton})
    void onFirstTeamButtonsClicked(View view) {
        int firstTeamGoalsCount = Integer.parseInt(firstTeamGoalPlusButton.getText().toString());
        int firstTeamPointsCount = Integer.parseInt(firstTeamPointPlusButton.getText().toString());
        switch (view.getId()) {
            case R.id.firstTeamGoalMinusButton:
                if (firstTeamGoalsCount > 0) {
                    firstTeamGoalsCount = firstTeamGoalsCount - 1;
                }
                break;
            case R.id.firstTeamGoalPlusButton:
                firstTeamGoalsCount = firstTeamGoalsCount + 1;
                break;
            case R.id.firstTeamPointPlusButton:
                firstTeamPointsCount = firstTeamPointsCount + 1;
                break;
            case R.id.firstTeamPointMinusButton:
                if (firstTeamPointsCount > 0) {
                    firstTeamPointsCount = firstTeamPointsCount - 1;
                }
                break;
        }
        int totalFirstTeamCount = firstTeamGoalsCount * 3 + firstTeamPointsCount;
        firstTeamGoalPlusButton.setText(getResources().getString(R.string.int_to_textview,firstTeamGoalsCount));
        firstTeamPointPlusButton.setText(getResources().getString(R.string.int_to_textview, firstTeamPointsCount));
        firstCommandsTotalPointsTextView.setText(getResources().getString(R.string.int_with_string_to_textview,totalFirstTeamCount , "pts"));
    }


    @OnClick({R.id.secondTeamGoalMinusButton, R.id.secondTeamGoalPlusButton, R.id.secondTeamPointPlusButton, R.id.secondTeamPointMinusButton})
    void onSecondTeamButtonsClicked(View view) {
        int secondTeamGoalsCount = Integer.parseInt(secondTeamGoalPlusButton.getText().toString());
        int secondTeamPointsCount = Integer.parseInt(secondTeamPointPlusButton.getText().toString());
        switch (view.getId()) {
            case R.id.secondTeamGoalMinusButton:
                if (secondTeamGoalsCount > 0) {
                    secondTeamGoalsCount = secondTeamGoalsCount - 1;
                }
                break;
            case R.id.secondTeamGoalPlusButton:
                secondTeamGoalsCount = secondTeamGoalsCount + 1;
                break;
            case R.id.secondTeamPointPlusButton:
                secondTeamPointsCount = secondTeamPointsCount + 1;
                break;
            case R.id.secondTeamPointMinusButton:
                if (secondTeamPointsCount > 0) {
                    secondTeamPointsCount = secondTeamPointsCount - 1;
                }
                break;
        }
        int totalSecondTeamCount = secondTeamGoalsCount * 3 + secondTeamPointsCount;
        secondTeamGoalPlusButton.setText(getResources().getString(R.string.int_to_textview, secondTeamGoalsCount));
        secondTeamPointPlusButton.setText(getResources().getString(R.string.int_to_textview, secondTeamPointsCount));
        secondCommandsTotalPointsTextView.setText(getResources().getString(R.string.int_with_string_to_textview,totalSecondTeamCount, "pts"));
    }

    @OnClick(R.id.createMessageButton)
    void onViewClicked() {
        InputMethodManager inputManager = (InputMethodManager) getActivity()
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
        ((MainActivity) getActivity())
                .showFragment(ResultFragment.newInstance(
                        socialTitleEditText.getText().toString(),
                        SystemClock.elapsedRealtime() - chronometer.getBase(),
                        tweetInformationEditText.getText().toString(),
                        tagEditText.getText().toString(),
                        firstCommandNameEditText.getText().toString(),
                        firstTeamGoalPlusButton.getText().toString(),
                        firstTeamPointPlusButton.getText().toString(),
                        firstCommandsTotalPointsTextView.getText().toString(),
                        secondCommandNameEditText.getText().toString(),
                        secondTeamGoalPlusButton.getText().toString(),
                        secondTeamPointPlusButton.getText().toString(),
                        secondCommandsTotalPointsTextView.getText().toString()
                ));
    }
}