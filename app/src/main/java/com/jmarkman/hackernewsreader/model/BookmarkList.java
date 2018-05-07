package com.jmarkman.hackernewsreader.model;

import android.databinding.ObservableArrayList;

import java.io.Serializable;

public class BookmarkList implements Serializable
{
    public ObservableArrayList<Bookmarks> bookmarkList;

    public BookmarkList()
    {
        bookmarkList = new ObservableArrayList<>();
    }

    public BookmarkList(ObservableArrayList<Bookmarks> bookmarks)
    {
        bookmarkList = bookmarks;
    }
}
