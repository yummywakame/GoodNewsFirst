/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yummywakame.breakroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * An {@link NewsArticleAdapter} knows how to create a list item layout for each article
 * in the data source (a list of {@link NewsArticle} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class NewsArticleAdapter extends ArrayAdapter<NewsArticle> {

    /**
     * Constructs a new {@link NewsArticleAdapter}.
     *
     * @param context      of the app
     * @param newsArticles is the list of newsArticles, which is the data source of the adapter
     */
    public NewsArticleAdapter(Context context, List<NewsArticle> newsArticles) {
        super(context, 0, newsArticles);
    }

    /**
     * Returns a list item view that displays information about the article at the given position
     * in the list of articles.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item, parent, false);
        }

        // Find the article at the given position in the list of articles
        NewsArticle currentNewsArticle = getItem(position);

        // Get the title from the NewsArticle object
        String newsTitle = currentNewsArticle.getTitle();
        // Find the TextView with view ID location
        TextView primaryLocationView = listItemView.findViewById(R.id.article_title);
        // Display the location of the current article in that TextView
        primaryLocationView.setText(newsTitle);

        // Create a new Date object from the time in milliseconds of the article
        String dateObject = formatDate(currentNewsArticle.getPublishedDate());

        // Find the TextView with view ID article_date
        TextView dateView = (TextView) listItemView.findViewById(R.id.article_date);
        // Format the article_date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the article_date of the current article in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = listItemView.findViewById(R.id.article_time);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Display the time of the current article in that TextView
        timeView.setText(formattedTime);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }


    /**
     * Return the formatted article_date string (i.e. "Mar 3, '84") from a Date object.
     */
    private String formatDate(String date) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yy");
//        return dateFormat.format(dateObject);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.getDefault());
        // Set the time zone to where the articles are published
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Date articleDate = simpleDateFormat.parse(date);
            // Set the time zone to where the articles are published
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            // Grab an instance of the calendar to get the time
            Calendar calendar = Calendar.getInstance();

            // Calculate time since article was posted
            long currentTime = calendar.getTimeInMillis();
            long articleTime = currentTime - articleDate.getTime();
            simpleDateFormat.applyPattern("MMM d, yy");
            return simpleDateFormat.format(articleDate);

        } catch (ParseException e) {
            return null;

        }
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(String date) {
//        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
//        return timeFormat.format(dateObject);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.getDefault());
        // Set the time zone to where the articles are published
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Date articleDate = simpleDateFormat.parse(date);
            // Set the time zone to where the articles are published
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            // Grab an instance of the calendar to get the time
            Calendar calendar = Calendar.getInstance();

            // Calculate time since article was posted
            long currentTime = calendar.getTimeInMillis();
            long articleTime = currentTime - articleDate.getTime();
            simpleDateFormat.applyPattern("h:mm a");
            return simpleDateFormat.format(articleDate);

        } catch (ParseException e) {
            return null;

        }
    }
}