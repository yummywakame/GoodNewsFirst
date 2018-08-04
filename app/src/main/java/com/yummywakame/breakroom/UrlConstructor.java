package com.yummywakame.breakroom;

import android.support.annotation.Nullable;
import android.util.Log;

import java.net.URL;

/**
 * GoodNewsFirst
 * Created by Olivia Meiring on 2018/08/02.
 * Yummy Wakame
 * olivia@yummy-wakame.com
 */
public final class UrlConstructor {

    // Tag for the log messages
    private static final String LOG_TAG = "UrlConstructor";

    // URL Base
    private static final String URL_BASE = "https://content.guardianapis.com/search?";

    // Chosen Segments: Sections or Tags
    public static final String TAG_GOODNEWS = "tag=world/series/the-upside-weekly-report";
    public static final String SECTION_US_NEWS = "section=us-news";
    public static final String SECTION_WORLD_NEWS = "section=world";
    public static final String SECTION_SPORT = "section=sport&editions=us/sport";
    public static final String SECTION_ART_AND_DESIGN = "section=artanddesign";
    public static final String SECTION_BUSINESS = "section=business&editions=us/business";
    public static final String SECTION_CULTURE = "section=culture&editions=us/culture";
    public static final String SECTION_EDUCATION = "section=education&editions=us/education";
    public static final String SECTION_ENVIRONMENT = "section=environment&editions=us/environment";
    public static final String SECTION_FILM = "section=film&editions=us/film";
    public static final String SECTION_LIFEANDSTYLE = "section=lifeandstyle&editions=us/lifeandstyle";
    public static final String SECTION_MUSIC = "section=music";
    public static final String SECTION_POLITICS = "tag=us-news/us-politics";
    public static final String SECTION_SCIENCE = "section=science";
    public static final String SECTION_TECHNOLOGY = "section=technology&editions=us/technology";
    public static final String SECTION_TRAVEL = "section=travel&editions=us/travel";
    public static final String SECTION_WOMEN_IN_LEADERSHIP = "section=women-in-leadership";

    // Extras at the end of the URL string
    private static final String URL_EXTRAS = "&show-fields=all&page-size=10&currentPage=1";

    /**
     * API Key Value which you need to store in your gradle.properties file as:
     * GoodNewsFirst_GuardianApp_ApiKey="YOUR-API-KEY-HERE"
     */
    private static final String apiKey = BuildConfig.ApiKey;

    // URL API Key
    private static final String URL_API_KEY = "&api-key=" + apiKey;

    /**
     * Returns a Guardian API URL string from all the components
     * @param segment section or tag in Guardian
     * @return URL string
     */
    public static String constructUrl(@Nullable String segment) {
        StringBuilder stringBuilder = new StringBuilder();

        // Add the URL Base
        stringBuilder.append(URL_BASE);

        // If the section isn't null then add that
        if (segment != null) stringBuilder.append(segment);

        // Add the extras
        stringBuilder.append(URL_EXTRAS);

        // Add the API Key
        stringBuilder.append(URL_API_KEY);

        // LOG the API URL
        Log.i(LOG_TAG, "API GUARDIAN_REQUEST_URL: "+ stringBuilder.toString());

        return stringBuilder.toString();
    }

}
