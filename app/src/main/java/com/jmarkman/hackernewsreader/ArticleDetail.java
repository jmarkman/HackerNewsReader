package com.jmarkman.hackernewsreader;

import android.databinding.DataBindingUtil;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jmarkman.hackernewsreader.databinding.ActivityArticleDetailBinding;

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
}
