package com.westmeath.gaa.westmeathscoreapp.mainview;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.westmeath.gaa.westmeathscoreapp.MainActivity;
import com.westmeath.gaa.westmeathscoreapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ResultFragment extends Fragment {

    private static final String SOCIAL_TITLE = "socialTitle";
    private static final String CHRONOMETER = "chronometer";
    private static final String TWEET_INFORMATION = "tweetInformation";
    private static final String TAG = "tag";
    private static final String FIRST_TEAM_NAME = "firstTeamName";
    private static final String FIRST_TEAM_GOALS = "firstTeamGoals";
    private static final String FIRST_TEAM_POINTS = "firstTeamPoints";
    private static final String FIRST_TEAM_TOTAL_POINTS = "firstTeamTotalPoints";
    private static final String SECOND_TEAM_NAME = "secondTeamName";
    private static final String SECOND_TEAM_GOALS = "secondTeamGoals";
    private static final String SECOND_TEAM_POINTS = "secondTeamPoints";
    private static final String SECOND_TEAM_TOTAL_POINTS = "secondTeamTotalPoints";
    @BindView(R.id.resultTextView)
    TextView resultTextView;

    private Unbinder unbinder;

    static Fragment newInstance(String socialTitle,
                                String chronometer,
                                String tweetInformation,
                                String tag,
                                String firstTeamName,
                                String firstTeamGoals,
                                String firstTeamPoints,
                                String firstTeamTotalPoints,
                                String secondTeamName,
                                String secondTeamGoals,
                                String secondTeamPoints,
                                String secondTeamTotalPoints) {
        ResultFragment fragment = new ResultFragment();

        Bundle args = new Bundle();
        args.putString(SOCIAL_TITLE, socialTitle);
        args.putString(CHRONOMETER, chronometer);
        args.putString(TWEET_INFORMATION, tweetInformation);
        args.putString(TAG, tag);
        args.putString(FIRST_TEAM_NAME, firstTeamName);
        args.putString(FIRST_TEAM_GOALS, firstTeamGoals);
        args.putString(FIRST_TEAM_POINTS, firstTeamPoints);
        args.putString(FIRST_TEAM_TOTAL_POINTS, firstTeamTotalPoints);
        args.putString(SECOND_TEAM_NAME, secondTeamName);
        args.putString(SECOND_TEAM_GOALS, secondTeamGoals);
        args.putString(SECOND_TEAM_POINTS, secondTeamPoints);
        args.putString(SECOND_TEAM_TOTAL_POINTS, secondTeamTotalPoints);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        unbinder = ButterKnife.bind(this, view);
        formatResultString();
    }

    private void formatResultString() {
        String socialTitle = "";
        String chronometer = "";
        String tweetInformation = "";
        String tag = "";
        String firstTeamName = "";
        String firstTeamGoals = "";
        String firstTeamPoints = "";
        String firstTeamTotalPoints = "";
        String secondTeamName = "";
        String secondTeamGoals = "";
        String secondTeamPoints = "";
        String secondTeamTotalPoints = "";
        if (getArguments() != null) {
            if (getArguments().getString(SOCIAL_TITLE) != null) {
                socialTitle = getArguments().getString(SOCIAL_TITLE);
            }
            if (getArguments().getString(CHRONOMETER) != null) {
                chronometer = getArguments().getString(CHRONOMETER);
            }
            if (getArguments().getString(TWEET_INFORMATION) != null) {
                tweetInformation = getArguments().getString(TWEET_INFORMATION);
            }
            if (getArguments().getString(TAG) != null) {
                tag = getArguments().getString(TAG);
            }
            if (getArguments().getString(FIRST_TEAM_NAME) != null) {
                firstTeamName = getArguments().getString(FIRST_TEAM_NAME);
            }
            if (getArguments().getString(FIRST_TEAM_GOALS) != null) {
                firstTeamGoals = getArguments().getString(FIRST_TEAM_GOALS);
            }
            if (getArguments().getString(FIRST_TEAM_POINTS) != null) {
                firstTeamPoints = getArguments().getString(FIRST_TEAM_POINTS);
            }
            if (getArguments().getString(FIRST_TEAM_TOTAL_POINTS) != null) {
                firstTeamTotalPoints = getArguments().getString(FIRST_TEAM_TOTAL_POINTS);
            }
            if (getArguments().getString(SECOND_TEAM_NAME) != null) {
                secondTeamName = getArguments().getString(SECOND_TEAM_NAME);
            }
            if (getArguments().getString(SECOND_TEAM_GOALS) != null) {
                secondTeamGoals = getArguments().getString(SECOND_TEAM_GOALS);
            }
            if (getArguments().getString(SECOND_TEAM_POINTS) != null) {
                secondTeamPoints = getArguments().getString(SECOND_TEAM_POINTS);
            }
            if (getArguments().getString(SECOND_TEAM_TOTAL_POINTS) != null) {
                secondTeamTotalPoints = getArguments().getString(SECOND_TEAM_TOTAL_POINTS);
            }
        }


        String resultMessage = getResources().getString(R.string.result_string_format,
                socialTitle,
                chronometer,
                tweetInformation,
                firstTeamName,
                firstTeamGoals,
                firstTeamPoints,
                firstTeamTotalPoints,
                secondTeamName,
                secondTeamGoals,
                secondTeamPoints,
                secondTeamTotalPoints,
                tag);
        resultTextView.setText(resultMessage);
    }

    @OnClick(R.id.postMessageButton)
    void onViewClicked() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, resultTextView.getText());
        shareIntent.setType("text/plain");
        startActivityForResult(Intent.createChooser(shareIntent,getResources().getString(R.string.share)), 21);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 21){
            ((MainActivity) getActivity()).onBackPressed();
        }
    }
}
