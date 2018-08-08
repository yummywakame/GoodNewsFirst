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

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<NewsArticle>> {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = MainActivity.class.getSimpleName() + " - LOG";

    /**
     * Constant value for the article loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int ARTICLE_LOADER_ID = 1;

    /**
     * ListView that holds the articles
     **/
    private ListView articleListView;

    /**
     * Adapter for the list of articles
     */
    private NewsArticleAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    /**
     * Swipe to reload spinner that is displayed while data is being downloaded
     */
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the menu toolbar for app compat
        Toolbar mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        // Hide the default title to use the designed one instead
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Lookup the swipe container view
        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setRefreshing(true);


        // Find a reference to the {@link ListView} in the layout
        articleListView = findViewById(R.id.list);

        // Find the feedback_view that is only visible when the list has no items
        mEmptyStateTextView = findViewById(R.id.feedback_view);
        articleListView.setEmptyView(mEmptyStateTextView);
        mEmptyStateTextView.setText("");

        // Create a new adapter that takes an empty list of articles as input
        mAdapter = new NewsArticleAdapter(this, new ArrayList<NewsArticle>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        articleListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected article.
        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current article that was clicked on
                NewsArticle currentNewsArticle = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri articleUri = Uri.parse(currentNewsArticle.getUrl());

                // Create a new intent to view the article URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // If there is a network connection, fetch data
        loadData();

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Check for internet connection and attempt to load data
                loadData();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
                R.color.primaryHilight,
                R.color.secondaryHilight,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);
    }

    @Override
    public Loader<List<NewsArticle>> onCreateLoader(int i, Bundle bundle) {

        // Get User Preferences or Defaults from Settings
        String SECTION_CHOICE = getPreferenceStringValue(R.string.pref_topic_key, R.string.pref_topic_default);


        //String MY_SECTION_CHOICE = getResources().getStringArray(R.array.pref_topic_urls)[R.string.pref_topic_key];
        // LOG the API URL
        Log.i(LOG_TAG, "pref_topic_urls: " + getResources().getStringArray(R.array.pref_topic_urls)[0]);

        String ORDER_BY = getPreferenceStringValue(R.string.pref_order_by_key, R.string.pref_order_by_default);
//    boolean hasThumbnails = getPreferenceBooleanValue(R.string.pref_thumbnail_key, R.bool.pref_thumbnail_default);
//    boolean hasContributors = getPreferenceBooleanValue(R.string.pref_contributors_key, R.bool.pref_contributors_default);

        // Construct the API URL to query the Guardian Dataset
        String GUARDIAN_SECTION = UrlConstructor.constructUrl(SECTION_CHOICE, ORDER_BY);

        // Create a new loader for the given URL
        return new NewsArticleLoader(this, GUARDIAN_SECTION);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsArticle>> loader, List<NewsArticle> newsArticles) {

        // Hide swipe to reload spinner
        swipeContainer.setRefreshing(false);


        // Set empty state text to display "No news articles found."
        mEmptyStateTextView.setText(R.string.no_articles);

        // Clear the adapter of previous article data
        mAdapter.clear();

        // If there is a valid list of newsFeed, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newsArticles != null && !newsArticles.isEmpty()) {
            mAdapter.addAll(newsArticles);
        } else {
            if (NewsQueryUtils.isConnected(getBaseContext())) {
                // Set empty state text to display "No articles found."
                mEmptyStateTextView.setText(R.string.no_articles);
            } else {
                // Update empty state with no connection error message
                mEmptyStateTextView.setText(R.string.no_internet_connection);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsArticle>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    @Override
    // This method initialize the contents of the Activity's options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    // This method is called whenever an button in the Toolbar is selected.
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                swipeContainer.setRefreshing(true);
                loadData();
                return true;
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Loads and reloads the data as requested
     */
    public void loadData() {
        // If there is a network connection, fetch data
        if (NewsQueryUtils.isConnected(getBaseContext())) {
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            getLoaderManager().initLoader(ARTICLE_LOADER_ID, null, this);

        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            swipeContainer.setRefreshing(false);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
            Toast.makeText(getApplicationContext(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();

        }
    }


    /**
     * A helper method to extract current preference String value
     *
     * @param key          preference's key
     * @param defaultValue preference's default value
     * @return preference current value
     */
    public String getPreferenceStringValue(int key, int defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString(
                getString(key),
                getString(defaultValue)
        );
    }

    /**
     * A helper method to extract current preference boolean value
     *
     * @param key          preference's key
     * @param defaultValue preference's default value
     * @return preference current value
     */
    public boolean getPreferenceBooleanValue(int key, int defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean(
                getString(key),
                getResources().getBoolean(defaultValue)
        );
    }
}