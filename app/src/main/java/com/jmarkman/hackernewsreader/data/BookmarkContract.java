package com.jmarkman.hackernewsreader.data;

import android.provider.BaseColumns;

public final class BookmarkContract
{
    public static final class BookmarksEntry implements BaseColumns
    {
        // Table name
        public static final String TABLE_NAME = "bookmarks";
        // Columns
        public static final String _ID = BaseColumns._ID;
        public static final String ARTICLE_TITLE = "title";
        public static final String SOURCE_URL = "url";
        public static final String ARTICLE_ID = "id";
        public static final String SUBMITTED_BY = "author";
        public static final String DATE_SUBMITTED = "date";
    }
}
