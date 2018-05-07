package com.jmarkman.hackernewsreader;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jmarkman.hackernewsreader.model.Bookmarks;

public class BookmarksList extends AppCompatActivity {

    Bookmarks bookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks_list);

        Intent intent = getIntent();
        bookmark = (Bookmarks) intent.getSerializableExtra("bookmarks");

        ActivityBookmarksBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_bookmarks_list);
        binding.setBookmarks(bookmark);
    }
}
