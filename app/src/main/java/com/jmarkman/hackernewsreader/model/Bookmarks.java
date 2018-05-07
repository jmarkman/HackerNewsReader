package com.jmarkman.hackernewsreader.model;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import java.io.Serializable;
import java.util.Observable;

public class Bookmarks implements Serializable
{
    public final ObservableInt bookmarkID = new ObservableInt();
    public final ObservableField<String> article_title = new ObservableField<>();
    public final ObservableField<String> source = new ObservableField<>();
    public final ObservableField<String> hn_id = new ObservableField<>();
    public final ObservableField<String> submitted_by = new ObservableField<>();
    public final ObservableField<String> date = new ObservableField<>();

    public Bookmarks() { }

    public Bookmarks(int id, String articleTitle, String urlSource, String siteID, String submittedBy, String submittedDate)
    {
        bookmarkID.set(id);
        article_title.set(articleTitle);
        source.set(urlSource);
        hn_id.set(siteID);
        submitted_by.set(submittedBy);
        date.set(submittedDate);
    }

}
