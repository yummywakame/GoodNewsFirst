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
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item, parent, false);
        }

        // Find the article at the given position in the list of articles
        NewsArticle currentNewsArticle = getItem(position);

        // Get and display the article's Section
        String newsSection = currentNewsArticle.getSectionName();
        TextView sectionNameView = listItemView.findViewById(R.id.article_section);
        sectionNameView.setText(newsSection);

        // Get and display the article's Title
        String newsTitle = currentNewsArticle.getTitle();
        TextView titleView = listItemView.findViewById(R.id.article_title);
        titleView.setText(newsTitle);

        // Get and display the article's Trail Text
        String newsTrail = currentNewsArticle.getTrailText();
        TextView trailView = listItemView.findViewById(R.id.article_trailtext);
        // Display the trailtext for the current article in that TextView or hide it if null
        if (newsTrail != null && !newsTrail.isEmpty()) {
            newsTrail = newsTrail + ".";
            trailView.setText(newsTrail);
        } else {
            trailView.setVisibility(View.GONE);
        }

        // Create a new Date object from the time in milliseconds of the article
        // Format the article_date string (i.e. "Mar 3, '18")
        String formattedDate = formatDate(currentNewsArticle.getPublishedDate());
        // Find and display the article's Date
        TextView dateView = listItemView.findViewById(R.id.article_date);
        dateView.setText(formattedDate);

        // Format the time string (i.e. "4:30 PM")
        String formattedTime = formatTime(currentNewsArticle.getPublishedDate());
        // Find and display the article's Time
        TextView timeView = listItemView.findViewById(R.id.article_time);
        timeView.setText(formattedTime);

        // Get and display the article's Author
        String newsAuthor = currentNewsArticle.getAuthor() + " ";
        TextView authorView = listItemView.findViewById(R.id.article_author);
        authorView.setText(newsAuthor);

        // Find and display the article's Thumbnail
        Bitmap newsPhoto = currentNewsArticle.getThumbnail();
        ImageView photoView = listItemView.findViewById(R.id.article_image);
        if (newsPhoto != null && NewsArticleLoader.mPrefThumbnail) {
            // If photo available or Thumbnail preference is true

            // Set Title maxlines and minlines to 3
            titleView.setMaxLines(3);
            titleView.setMinLines(3);

            // target and increase the aspect-ratio for photoView to 16:9
            ConstraintLayout constraintLayout = listItemView.findViewById(R.id.newslist_constraint_layout);
            ConstraintSet set = new ConstraintSet();
            set.clone(constraintLayout);
            set.setDimensionRatio(photoView.getId(), "16:9");
            set.applyTo(constraintLayout);
            photoView.setImageBitmap(newsPhoto);

            // To clip the end of the article_title to end of the article_image (thumbnail):
            // First break the existing constraint connection.
            set.clear(R.id.article_title, ConstraintSet.START);
            // Then attach a new constraint connection.
            set.connect(R.id.article_title, ConstraintSet.END, R.id.article_image, ConstraintSet.END, 0);
            set.applyTo(constraintLayout);

        } else {
            // Remove photo and change layout:
            // Increase Title maxlines and minlines from 3 to 4
            titleView.setMaxLines(4);
            titleView.setMinLines(4);

            // target and decrease the aspect-ratio for photoView to 16:4
            ConstraintLayout constraintLayout = listItemView.findViewById(R.id.newslist_constraint_layout);
            ConstraintSet set = new ConstraintSet();
            set.clone(constraintLayout);
            set.setDimensionRatio(photoView.getId(), "16:4");
            set.applyTo(constraintLayout);
            // set background color in lieu of thumbnail image
            photoView.setImageResource(R.drawable.no_thumbnail_bg);
            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // To clip the end of article_title to start of article_time (bookmark):
            // First break the constraint connection.
            set.clear(R.id.article_title, ConstraintSet.END);
            // Then attach a new constraint connection.
            set.connect(R.id.article_title, ConstraintSet.END, R.id.article_time, ConstraintSet.START, 0);
            set.connect(R.id.article_title, ConstraintSet.START, R.id.article_image, ConstraintSet.START, 0);
            set.applyTo(constraintLayout);
        }

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
        final SimpleDateFormat outputFormatter = new SimpleDateFormat("MMM dd ''yy", Locale.US);
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