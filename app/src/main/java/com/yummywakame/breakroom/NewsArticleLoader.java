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

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of articles by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class NewsArticleLoader extends AsyncTaskLoader<List<NewsArticle>> {

    /** Query URL */
    private String mUrl;
    public static boolean mPrefThumbnail;

    /**
     * Constructs a new {@link NewsArticleLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     * @param prefThumbnail to load data from
     */
    public NewsArticleLoader(Context context, String url, Boolean prefThumbnail) {
        super(context);
        mUrl = url;
        mPrefThumbnail = prefThumbnail;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<NewsArticle> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of newsArticles.
        List<NewsArticle> newsArticles = NewsQueryUtils.fetchArticleData(mUrl);
        return newsArticles;
    }
}