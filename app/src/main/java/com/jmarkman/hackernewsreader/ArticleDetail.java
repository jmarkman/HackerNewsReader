package com.jmarkman.hackernewsreader;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jmarkman.hackernewsreader.data.BookmarkContract;
import com.jmarkman.hackernewsreader.data.DatabaseHelper;
import com.jmarkman.hackernewsreader.databinding.ActivityArticleDetailBinding;
import com.jmarkman.hackernewsreader.data.BookmarkContract.BookmarksEntry;

public class ArticleDetail extends AppCompatActivity implements View.OnClickListener
{
    private String[] databaseCols = { BookmarksEntry._ID, BookmarksEntry.ARTICLE_TITLE, BookmarksEntry.SOURCE_URL, BookmarksEntry.ARTICLE_ID, BookmarksEntry.SUBMITTED_BY, BookmarksEntry.DATE_SUBMITTED };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Article article = getIntent().getParcelableExtra("Article");

        ActivityArticleDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_article_detail);

        binding.setArticle(article);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // https://stackoverflow.com/q/48034351
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                // This calls onDestroy(), which schedules the activity for garbage collection
                this.finish();
                // If this doesn't return true, the FrontPage main activity's onCreate method is called again
                // Post-project: learn about why this happens
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createBookmark()
    {
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase database = helper.getWritableDatabase();

        Article article = getIntent().getParcelableExtra("Article");

        boolean articleInBookmarks = checkIfArticleInDatabase(helper, article.articleID);

        if (articleInBookmarks)
        {
            ContentValues values = new ContentValues();
            values.put(BookmarksEntry.ARTICLE_TITLE, article.articleTitle);
            values.put(BookmarksEntry.SOURCE_URL, article.articleUrl);
            values.put(BookmarksEntry.ARTICLE_ID, article.articleID);
            values.put(BookmarksEntry.SUBMITTED_BY, article.submittedBy);
            values.put(BookmarksEntry.DATE_SUBMITTED, article.submissionTime);

            database.insert(BookmarksEntry.TABLE_NAME, null, values);
            database.close();
        }
        else
        {
            Toast.makeText(this, "This article is already in your bookmarks", Toast.LENGTH_LONG).show();
        }

        database.close();
    }

    private boolean checkIfArticleInDatabase(DatabaseHelper helper, String articleID)
    {
        boolean articleInBookmarks = false;

        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query(BookmarksEntry.TABLE_NAME, databaseCols, BookmarksEntry.ARTICLE_ID + " LIKE " + articleID, null, null,null,null);

        if (cursor.getCount() > 0)
        {
            articleInBookmarks = true;
            cursor.close();
        }

        cursor.close();
        return articleInBookmarks;
    }

    @Override
    public void onClick(View view) {
        createBookmark();
    }
}
