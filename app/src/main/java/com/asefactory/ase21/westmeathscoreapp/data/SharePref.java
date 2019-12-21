package com.asefactory.ase21.westmeathscoreapp.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharePref {

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
    private static final String IS_FIRST_HALF_FLAG = "isFirstHalfIsFinished";
    private static final String CHRONOMETER_FLAG = "chronometerStarted";
    private static final String SPEND_TIME = "spendTime";

    private static SharePref sharePref;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharePref(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharePref getInstance(Context context) {
        if (sharePref == null) {
            sharePref = new SharePref(context);
        }
        return sharePref;
    }

    public void putTitle(String title) {
        editor.putString(SOCIAL_TITLE, title);
        editor.commit();
    }

    public void putTweetInformation(String tweet) {
        editor.putString(TWEET_INFORMATION, tweet);
        editor.commit();
    }

    public void putTag(String tag) {
        editor.putString(TAG, tag);
        editor.commit();
    }

    public void putTime(String chronometer) {
        editor.putString(CHRONOMETER, chronometer);
        editor.commit();
    }

    public void putFirstCommandName(String firstTeamName) {
        editor.putString(FIRST_TEAM_NAME, firstTeamName);
        editor.commit();
    }

    public void putFirstTeamGoal(String firstTeamGoals) {
        editor.putString(FIRST_TEAM_GOALS, firstTeamGoals);
        editor.commit();
    }

    public void putFirstTeamPoint(String firstTeamPoints) {
        editor.putString(FIRST_TEAM_POINTS, firstTeamPoints);
        editor.commit();
    }

    public void putFirstCommandsTotalPoints(String firstTeamTotalPoints) {
        editor.putString(FIRST_TEAM_TOTAL_POINTS, firstTeamTotalPoints);
        editor.commit();
    }

    public void putSecondCommandName(String secondTeamName) {
        editor.putString(SECOND_TEAM_NAME, secondTeamName);
        editor.commit();
    }

    public void putSecondTeamGoal(String secondTeamGoals) {
        editor.putString(SECOND_TEAM_GOALS, secondTeamGoals);
        editor.commit();
    }

    public void putSecondTeamPoint(String secondTeamPoints) {
        editor.putString(SECOND_TEAM_POINTS, secondTeamPoints);
        editor.commit();
    }

    public void putSecondCommandsTotalPoints(String secondTeamTotalPoints) {
        editor.putString(SECOND_TEAM_TOTAL_POINTS, secondTeamTotalPoints);
        editor.commit();
    }

    public void putFirstHalfFlag(boolean isFirstHalfIsFinished) {
        editor.putBoolean(IS_FIRST_HALF_FLAG, isFirstHalfIsFinished);
        editor.commit();
    }

    public void putChronometerFlag(boolean chronometerStarted) {
        editor.putBoolean(CHRONOMETER_FLAG, chronometerStarted);
        editor.commit();
    }

    public String getSocialTitle(){
        return sharedPreferences.getString(SOCIAL_TITLE, "");
    }

    public String getTweetInformation(){
        return sharedPreferences.getString(TWEET_INFORMATION, "");
    }

    public String getTag(){
        return sharedPreferences.getString(TAG, "");
    }

    public String getTime(){
        return sharedPreferences.getString(CHRONOMETER, "");
    }

    public boolean isFirstHalfFlag(){
        return sharedPreferences.getBoolean(IS_FIRST_HALF_FLAG, false);
    }

    public boolean isChronometerStarted(){
        return sharedPreferences.getBoolean(CHRONOMETER_FLAG, false);
    }

    public String getFirstTeamName(){
         return sharedPreferences.getString(FIRST_TEAM_NAME, "");
    }
    public String getFirstTeamGoals(){
        return sharedPreferences.getString(FIRST_TEAM_GOALS, "");
    }
    public String getFirstTeamPoints(){
        return sharedPreferences.getString(FIRST_TEAM_POINTS, "");
    }
    public String getFirstTeamTotalPoints(){
        return sharedPreferences.getString(FIRST_TEAM_TOTAL_POINTS, "");
    }

    public String getSecondTeamName(){
         return sharedPreferences.getString(SECOND_TEAM_NAME, "");
    }
    public String getSecondTeamGoals(){
        return sharedPreferences.getString(SECOND_TEAM_GOALS, "");
    }
    public String getSecondTeamPoints(){
        return sharedPreferences.getString(SECOND_TEAM_POINTS, "");
    }
    public String getSecondTeamTotalPoints(){
        return sharedPreferences.getString(SECOND_TEAM_TOTAL_POINTS, "");
    }

    public void putSpendTime(long spendTime) {
        editor.putLong(SPEND_TIME, spendTime);
        editor.commit();
    }

    public long getSpendTime() {
        return sharedPreferences.getLong(SPEND_TIME, 0L);
    }
}
