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

import android.graphics.Bitmap;

/**
 * An {@link NewsArticle} object contains information related to a single article.
 */
public class NewsArticle {

    // Section the article comes from
    private String webSectionName;

    // Publication date for the article
    private String webPublicationDate;

    // Title of the article
    private String webTitle;

    // Intro of the article
    private String webTrailText;

    // Url of the article
    private String webUrl;

    // Author of the article
    private String byLine;

    // Bitmap of the thumbnail of the article
    private Bitmap thumbnail;

    /**
     * Constructs a new {@link NewsArticle} object
     *
     * @param webSectionName     Section for the article
     * @param webPublicationDate Publication date for the article
     * @param webTitle           Title of the article
     * @param webTrailText       TrailText of the article
     * @param webUrl             Url of the article
     * @param byLine             Author of the article
     * @param thumbnail          Url to the thumbnail of the article
     */
    public NewsArticle(String webSectionName, String webPublicationDate, String webTitle, String webTrailText, String webUrl, String byLine, Bitmap thumbnail) {
        this.webSectionName = webSectionName;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webTrailText = webTrailText;
        this.webUrl = webUrl;
        this.byLine = byLine;
        this.thumbnail = thumbnail;
    }
    public String getSectionName() {
        return webSectionName;
    }

    public String getPublishedDate() {
        return webPublicationDate;
    }

    public String getTitle() {
        return webTitle;
    }

    public String getTrailText() {
        return webTrailText;
    }

    public String getUrl() {
        return webUrl;
    }

    public String getAuthor() {
        return byLine;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }
}
