package com.asefactory.ase21.westmeathscoreapp.mainview;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.asefactory.ase21.westmeathscoreapp.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ResultFragment extends Fragment {

    private static final String SOCIAL_TITLE = "socialTitle";
    private static final String CHRONOMETER = "chronometer";
    private static final String TWEET_INFORMATION = "tweetInformation";
    private static final String TAG = "tag";
    private static final String FIRST_TEAM_NAME = "firstTeamName";
    private static final String FIRST_TEAM_GOALS  = "firstTeamGoals";
    private static final String FIRST_TEAM_POINTS = "firstTeamPoints";
    private static final String FIRST_TEAM_TOTAL_POINTS = "firstTeamTotalPoints";
    private static final String SECOND_TEAM_NAME = "secondTeamName";
    private static final String SECOND_TEAM_GOALS = "secondTeamGoals";
    private static final String SECOND_TEAM_POINTS = "secondTeamPoints";
    private static final String SECOND_TEAM_TOTAL_POINTS = "secondTeamTotalPoints";

    private Unbinder unbinder;

    static Fragment newInstance(String socialTitle,
                                       long chronometer,
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
        args.putLong(CHRONOMETER, chronometer);
        args.putString(TWEET_INFORMATION, tweetInformation);
        args.putString(TAG, tag);
        args.putString(FIRST_TEAM_NAME, firstTeamName);
        args.putString(FIRST_TEAM_GOALS, firstTeamGoals);
        args.putString(FIRST_TEAM_POINTS, firstTeamPoints);
        args.putString(FIRST_TEAM_TOTAL_POINTS, firstTeamTotalPoints);
        args.putString(SECOND_TEAM_NAME,  secondTeamName);
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
    }
}
