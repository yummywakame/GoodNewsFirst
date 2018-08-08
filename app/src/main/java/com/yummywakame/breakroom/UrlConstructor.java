package com.yummywakame.breakroom;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

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

    // Section choices:
//    public static final String TAG_GOODNEWS = MyApplication.getAppContext().getResources().getString(R.string.pref_topic_0_label_url);
//    public static final String SECTION_US_NEWS= MyApplication.getAppContext().getResources().getString(R.string.pref_topic_1_label_url);
//    public static final String SECTION_WORLD_NEWS= MyApplication.getAppContext().getResources().getString(R.string.pref_topic_2_label_url);
//    public static final String SECTION_SPORT= MyApplication.getAppContext().getResources().getString(R.string.pref_topic_3_label_url);
//    public static final String SECTION_BUSINESS= MyApplication.getAppContext().getResources().getString(R.string.pref_topic_4_label_url);
//    public static final String SECTION_POLITICS= MyApplication.getAppContext().getResources().getString(R.string.pref_topic_5_label_url);
//    public static final String SECTION_TECHNOLOGY= MyApplication.getAppContext().getResources().getString(R.string.pref_topic_6_label_url);
//    public static final String SECTION_ART_AND_DESIGN= MyApplication.getAppContext().getResources().getString(R.string.pref_topic_7_label_url);
//    public static final String SECTION_CULTURE= MyApplication.getAppContext().getResources().getString(R.string.pref_topic_8_label_url);
//    public static final String SECTION_EDUCATION= MyApplication.getAppContext().getResources().getString(R.string.pref_topic_9_label_url);
//    public static final String SECTION_ENVIRONMENT= MyApplication.getAppContext().getResources().getString(R.string.pref_topic_10_label_url);
//    public static final String SECTION_FILM= MyApplication.getAppContext().getResources().getString(R.string.pref_topic_11_label_url);
//    public static final String SECTION_LIFEANDSTYLE= MyApplication.getAppContext().getResources().getString(R.string.pref_topic_12_label_url);
//    public static final String SECTION_MUSIC= MyApplication.getAppContext().getResources().getString(R.string.pref_topic_13_label_url);
//    public static final String SECTION_SCIENCE= MyApplication.getAppContext().getResources().getString(R.string.pref_topic_14_label_url);
//    public static final String SECTION_TRAVEL= MyApplication.getAppContext().getResources().getString(R.string.pref_topic_15_label_url);
//    public static final String SECTION_WOMEN_IN_LEADERSHIP= MyApplication.getAppContext().getResources().getString(R.string.pref_topic_16_label_url);

    // Extras at the end of the URL string
    private static final String URL_EXTRAS = "&show-fields=all";


    /**
     * API Key Value which you need to store in your gradle.properties file as:
     * GoodNewsFirst_GuardianApp_ApiKey="YOUR-API-KEY-HERE"
     */
    private static final String apiKey = BuildConfig.ApiKey;

    // URL API Key
    private static final String URL_API_KEY = "&api-key=" + apiKey;

    /**
     * Returns a Guardian API URL string from all the components
     * @param section section or tag in Guardian
     * @return URL string
     */
    public static String constructUrl(@Nullable String section, @Nullable String orderBy) {

        // Map URLs to section preference so it can be used as a variable name
        Map<String, String> vars = new HashMap<>();
        vars.put(
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_0_label_value),
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_0_label_url));
        vars.put(
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_1_label_value),
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_1_label_url));
        vars.put(
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_2_label_value),
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_2_label_url));
        vars.put(
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_3_label_value),
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_3_label_url));
        vars.put(
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_4_label_value),
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_4_label_url));
        vars.put(
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_5_label_value),
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_5_label_url));
        vars.put(
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_6_label_value),
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_6_label_url));
        vars.put(
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_7_label_value),
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_7_label_url));
        vars.put(
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_8_label_value),
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_8_label_url));
        vars.put(
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_9_label_value),
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_9_label_url));
        vars.put(
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_10_label_value),
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_10_label_url));
        vars.put(
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_11_label_value),
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_11_label_url));
        vars.put(
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_12_label_value),
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_12_label_url));
        vars.put(
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_13_label_value),
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_13_label_url));
        vars.put(
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_14_label_value),
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_14_label_url));
        vars.put(
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_15_label_value),
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_15_label_url));
        vars.put(
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_16_label_value),
                MyApplication.getAppContext().getResources().getString(R.string.pref_topic_16_label_url));

        // Get section's variable name from the map
        String formattedSection = vars.get(section);

        // Start the StringBuilder and add the URL Base to the beginning
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(URL_BASE);

        // If the section isn't null then add that
        if (section != null) {
            stringBuilder.append(formattedSection);
        } else {
            stringBuilder.append(MyApplication.getAppContext().getResources().getString(R.string.pref_topic_0_label_url));
        }

        // If the orderBy isn't null then add that
        if (orderBy != null) {
            stringBuilder.append("&orderBy="
                    + orderBy);
        } else {
            stringBuilder.append("&orderBy="
                    + MyApplication.getAppContext().getResources().getString(R.string.pref_order_by_default));
        }

        // Add the extras
        stringBuilder.append(URL_EXTRAS);

        // Add the API Key
        stringBuilder.append(URL_API_KEY);

        // LOG the API URL
        Log.i(LOG_TAG, "API GUARDIAN_REQUEST_URL: "+ stringBuilder.toString());

        return stringBuilder.toString();
    }

}
