package com.westmeath.gaa.westmeathscoreapp.mainview;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
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

import com.westmeath.gaa.westmeathscoreapp.MainActivity;
import com.westmeath.gaa.westmeathscoreapp.R;
import com.westmeath.gaa.westmeathscoreapp.data.SharePref;

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
        loadSavedState();
    }

    private void loadSavedState() {
        chronometerStarted = SharePref.getInstance(getContext()).isChronometerStarted();
        isFirstHalfIsFinished = SharePref.getInstance(getContext()).isFirstHalfFlag();
        socialTitleEditText.setText(SharePref.getInstance(getContext()).getSocialTitle());
        tweetInformationEditText.setText(SharePref.getInstance(getContext()).getTweetInformation());
        tagEditText.setText(SharePref.getInstance(getContext()).getTag());
        timeTextView.setText(SharePref.getInstance(getContext()).getTime());

        firstCommandNameEditText.setText(SharePref.getInstance(getContext()).getFirstTeamName());
        firstTeamGoalPlusButton.setText(SharePref.getInstance(getContext()).getFirstTeamGoals());
        firstTeamPointPlusButton.setText(SharePref.getInstance(getContext()).getFirstTeamPoints());
        firstCommandsTotalPointsTextView.setText(SharePref.getInstance(getContext()).getFirstTeamTotalPoints());

        secondCommandNameEditText.setText(SharePref.getInstance(getContext()).getSecondTeamName());
        secondTeamGoalPlusButton.setText(SharePref.getInstance(getContext()).getSecondTeamGoals());
        secondTeamPointPlusButton.setText(SharePref.getInstance(getContext()).getSecondTeamPoints());
        secondCommandsTotalPointsTextView.setText(SharePref.getInstance(getContext()).getSecondTeamTotalPoints());

        if (!timeTextView.getText().toString().isEmpty()) {
            initChronometer(SharePref.getInstance(getContext()).getSpendTime());
        }
    }

    @OnClick(R.id.startChronometerButton)
    void onStartChronometerClicked() {
        if (timeTextView.getText().toString().isEmpty()) {
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
                chronometer.setBase(SystemClock.elapsedRealtime());
                setChronometerClickListener(firstHalfTime);
                chronometer.start();
                chronometerStarted = true;
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
                chronometer.setBase(SystemClock.elapsedRealtime() - firstHalfTime);
                setChronometerClickListener(secondHalfTime);
                chronometer.start();
                chronometerStarted = true;
                startChronometerButton.setText(getResources().getText(R.string.full_time));
                halfNameTextView.setText(getResources().getText(R.string.second_half));
            } else {
                chronometer.stop();
                chronometer.setTextColor(getResources().getColor(android.R.color.black));
                startChronometerButton.setText(getResources().getText(R.string.start));
                isFirstHalfIsFinished = false;
                chronometerStarted = false;
            }
        }
    }

    private void initChronometer(long restoredTime) {
        checkValidTextFields();
        if (!isFirstHalfIsFinished) {
            if (!chronometerStarted) {
                startChronometerButton.setText(getResources().getText(R.string.start));
            } else {
                halfNameTextView.setText(getResources().getText(R.string.first_half));
                chronometer.setBase(restoredTime);
                startChronometerButton.setText(getResources().getText(R.string.half_time));
                setChronometerClickListener(firstHalfTime);
                chronometer.start();
            }
        } else {
            if (!chronometerStarted) {
                halfNameTextView.setText(getResources().getText(R.string.first_half));
                chronometer.setBase(SystemClock.elapsedRealtime() - firstHalfTime);
                startChronometerButton.setText(getResources().getText(R.string.second_half));
            } else {
                halfNameTextView.setText(getResources().getText(R.string.second_half));
                chronometer.setBase(restoredTime);
                startChronometerButton.setText(getResources().getText(R.string.full_time));
                setChronometerClickListener(secondHalfTime);
                chronometer.start();
            }
        }
    }

    private void setChronometerClickListener(long firstHalfTime) {
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
        firstTeamGoalPlusButton.setText(getResources().getString(R.string.int_to_textview, firstTeamGoalsCount));
        firstTeamPointPlusButton.setText(getResources().getString(R.string.int_to_textview, firstTeamPointsCount));
        firstCommandsTotalPointsTextView.setText(getResources().getString(R.string.int_with_string_to_textview, totalFirstTeamCount, "pts"));
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
        secondCommandsTotalPointsTextView.setText(getResources().getString(R.string.int_with_string_to_textview, totalSecondTeamCount, "pts"));
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
                        chronometer.getText().toString(),
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

    @OnClick(R.id.resetMessageButton)
    void onResetMessageButtonClicked() {
        socialTitleEditText.setText("");
        tweetInformationEditText.setText("");
        tagEditText.setText("");
        firstCommandNameEditText.setText("");
        firstTeamGoalPlusButton.setText("0");
        firstTeamPointPlusButton.setText("0");
        firstCommandsTotalPointsTextView.setText("0 pts");
        secondCommandNameEditText.setText("");
        secondTeamGoalPlusButton.setText("0");
        secondTeamPointPlusButton.setText("0");
        secondCommandsTotalPointsTextView.setText("0 pts");
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setTextColor(getResources().getColor(android.R.color.black));
        startChronometerButton.setText(getResources().getText(R.string.start));
        chronometer.stop();
        timeTextView.setText("");
        isFirstHalfIsFinished = false;
        chronometerStarted = false;
        halfNameTextView.setText(getResources().getText(R.string.first_half));
    }

    @Override
    public void onStop() {
        Log.d("ase21", "onStop: onStop");
        saveCurrentState();
        super.onStop();
    }

    private void saveCurrentState() {
        SharePref.getInstance(getContext()).putTitle(socialTitleEditText.getText().toString());
        SharePref.getInstance(getContext()).putTweetInformation(tweetInformationEditText.getText().toString());
        SharePref.getInstance(getContext()).putTag(tagEditText.getText().toString());
        SharePref.getInstance(getContext()).putTime(timeTextView.getText().toString());
        SharePref.getInstance(getContext()).putFirstHalfFlag(isFirstHalfIsFinished);
        SharePref.getInstance(getContext()).putChronometerFlag(chronometerStarted);

        SharePref.getInstance(getContext()).putFirstCommandName(firstCommandNameEditText.getText().toString());
        SharePref.getInstance(getContext()).putFirstTeamGoal(firstTeamGoalPlusButton.getText().toString());
        SharePref.getInstance(getContext()).putFirstTeamPoint(firstTeamPointPlusButton.getText().toString());
        SharePref.getInstance(getContext()).putFirstCommandsTotalPoints(firstCommandsTotalPointsTextView.getText().toString());

        SharePref.getInstance(getContext()).putSecondCommandName(secondCommandNameEditText.getText().toString());
        SharePref.getInstance(getContext()).putSecondTeamGoal(secondTeamGoalPlusButton.getText().toString());
        SharePref.getInstance(getContext()).putSecondTeamPoint(secondTeamPointPlusButton.getText().toString());
        SharePref.getInstance(getContext()).putSecondCommandsTotalPoints(secondCommandsTotalPointsTextView.getText().toString());

        long l = chronometer.getBase();
        SharePref.getInstance(getContext()).putSpendTime(l);
    }
}