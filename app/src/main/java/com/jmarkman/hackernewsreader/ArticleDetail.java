package com.jmarkman.hackernewsreader;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jmarkman.hackernewsreader.data.DatabaseHelper;
import com.jmarkman.hackernewsreader.databinding.ActivityArticleDetailBinding;
import com.jmarkman.hackernewsreader.data.BookmarkContract.BookmarksEntry;

public class ArticleDetail extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Article article = getIntent().getParcelableExtra("Article");

        ActivityArticleDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_article_detail);

        binding.setArticle(article);

        createBookmark();
    }

    private void createBookmark()
    {
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookmarksEntry.ARTICLE_TITLE, "How to become a bank (2016)");
        values.put(BookmarksEntry.SOURCE_URL, "https://www.bankofengland.co.uk/-/media/boe/files/prudential-regulation/new-bank/seminar-slides-1010.pdf?la=en&hash=CC03BF5728A8DD96D28AE26DEFB3E95BF1F03333");
        values.put(BookmarksEntry.ARTICLE_ID, "16920125");
        values.put(BookmarksEntry.SUBMITTED_BY, "hestefisk");
        values.put(BookmarksEntry.DATE_SUBMITTED, "04-25-2018");

        database.insert(BookmarksEntry.TABLE_NAME, null, values);
    }
}
