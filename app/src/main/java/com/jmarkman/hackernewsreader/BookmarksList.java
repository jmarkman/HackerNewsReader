package com.jmarkman.hackernewsreader;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jmarkman.hackernewsreader.data.BookmarkContract;
import com.jmarkman.hackernewsreader.data.DatabaseHelper;
import com.jmarkman.hackernewsreader.data.BookmarkContract.BookmarksEntry;

public class BookmarksList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks_list);

        DatabaseHelper helper = new DatabaseHelper(this);
        new BookmarkQueryTask().execute(helper);
    }


    public class BookmarkQueryTask extends AsyncTask<DatabaseHelper, Void, Cursor>
    {

        @Override
        protected Cursor doInBackground(DatabaseHelper... databaseHelpers) {
            SQLiteDatabase database = databaseHelpers[0].getReadableDatabase();

            String[] columns = {
                    BookmarksEntry.ARTICLE_TITLE,
                    BookmarksEntry.SOURCE_URL,
                    BookmarksEntry.DATE_SUBMITTED,
                    BookmarksEntry.SUBMITTED_BY
            };

            Cursor cursor = database.query(BookmarksEntry.TABLE_NAME, columns, null, null, null, null, null);

            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor bookmarksCursor)
        {

        }
    }
}
