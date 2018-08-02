package com.yummywakame.breakroom;

import android.support.annotation.Nullable;

/**
 * GoodNewsFirst
 * Created by Olivia Meiring on 2018/08/02.
 * Yummy Wakame
 * olivia@yummy-wakame.com
 */
public final class UrlConstructor {

    // URL Base
    private static final String URL_BASE = "https://content.guardianapis.com/search?";

    // Chosen Segments: Sections or Tags
    public static final String TAG_GOODNEWS = "tag=world%2Fseries%2Fthe-upside-weekly-report";
    public static final String SECTION_NEWS = "section=news";

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

        return stringBuilder.toString();
    }

}
