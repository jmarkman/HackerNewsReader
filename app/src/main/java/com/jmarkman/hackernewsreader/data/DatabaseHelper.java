package com.jmarkman.hackernewsreader.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.jmarkman.hackernewsreader.data.BookmarkContract.BookmarksEntry;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "hnreader.db";
    private static final int DATABASE_VERSION = 1;

    // SQL for creating bookmarks table
    private static final String TABLE_BOOKMARKS_CREATE =
            "CREATE TABLE " + BookmarksEntry.TABLE_NAME + " (" +
                    BookmarksEntry._ID + " INTEGER PRIMARY KEY, " +
                    BookmarksEntry.ARTICLE_TITLE + " TEXT, " +
                    BookmarksEntry.SOURCE_URL + " TEXT, " +
                    BookmarksEntry.ARTICLE_ID + " TEXT, " +
                    BookmarksEntry.SUBMITTED_BY + " TEXT, " +
                    BookmarksEntry.DATE_SUBMITTED + " TEXT " +
                    ")";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        database.execSQL(TABLE_BOOKMARKS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVer, int newVer)
    {
        database.execSQL("DROP TABLE IF EXISTS " + BookmarksEntry.TABLE_NAME);
        onCreate(database);
    }
}
