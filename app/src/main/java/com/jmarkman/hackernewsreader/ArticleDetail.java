package com.jmarkman.hackernewsreader;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
    }
}
