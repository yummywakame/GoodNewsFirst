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
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    public NewsArticleAdapter(@NonNull Context context, @NonNull List<NewsArticle> newsArticles) {
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

        // Get the Section name from the NewsArticle object
        String newsSection = currentNewsArticle.getSectionName();
        // Find the TextView with view ID article_title
        TextView sectionNameView = listItemView.findViewById(R.id.article_section);
        // Display the location of the current article in that TextView
        sectionNameView.setText(newsSection);

        // Get the title from the NewsArticle object
        String newsTitle = currentNewsArticle.getTitle();
        // Find the TextView with view ID article_title
        TextView titleView = listItemView.findViewById(R.id.article_title);
        // Display the location of the current article in that TextView
        titleView.setText(newsTitle);

        // Get the trailtext from the NewsArticle object
        String newsTrail = currentNewsArticle.getTrailText() + ".";
        // Find the TextView with view ID trail article_trailtext
        TextView trailView = listItemView.findViewById(R.id.article_trailtext);
        // Display the location of the current article in that TextView
        trailView.setText(newsTrail);

        // Create a new Date object from the time in milliseconds of the article
        // Format the article_date string (i.e. "Mar 3, '18")
        String formattedDate = formatDate(currentNewsArticle.getPublishedDate());
        // Find the TextView with view ID article_date
        TextView dateView = listItemView.findViewById(R.id.article_date);
        // Display the article_date of the current article in that TextView
        dateView.setText(formattedDate);

        // Format the time string (i.e. "4:30 PM")
        String formattedTime = formatTime(currentNewsArticle.getPublishedDate());
        // Find the TextView with view ID article_time
        TextView timeView = listItemView.findViewById(R.id.article_time);
        // Display the time of the current article in that TextView
        timeView.setText(formattedTime);

        // Get the author from the NewsArticle object
        String newsAuthor = "By " + currentNewsArticle.getAuthor() + " ";
        // Find the TextView with view ID article_author
        TextView authorView = listItemView.findViewById(R.id.article_author);
        // Display the location of the current article in that TextView
        authorView.setText(newsAuthor);

        // Get the Bitmap image from the NewsArticle object
        Bitmap newsPhoto = currentNewsArticle.getThumbnail();
        // Find the ImageView with the ID article_image
        ImageView photoView = listItemView.findViewById(R.id.article_image);
        // Display the image in that ImageView
        photoView.setImageBitmap(newsPhoto);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

    /**
     * Return a formatted time string (i.e. "Mar 3, '18") from a Date object.
     */
    private String formatDate(String date) {
        final SimpleDateFormat inputParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());

        Date date_out = null;
        try {
            date_out = inputParser.parse(date);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        final SimpleDateFormat outputFormatter = new SimpleDateFormat("MMM d ''yy", Locale.US);
        return outputFormatter.format(date_out);
    }

    /**
     * Return a formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(String date) {
        final SimpleDateFormat inputParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());

        Date date_out = null;
        try {
            date_out = inputParser.parse(date);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        final SimpleDateFormat outputFormatter = new SimpleDateFormat("h:mm a", Locale.US);
        return outputFormatter.format(date_out);
    }
}