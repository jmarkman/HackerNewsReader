package com.jmarkman.hackernewsreader;

import android.net.Uri;
import android.util.Log;

import java.net.URL;

public class HackerNewsAPI
{
    private static final String API_URL = "https://hacker-news.firebaseio.com";

    private HackerNewsAPI() { }

    /**
     * Constructs the top stories API url
     * @return the top stories API url as a URL
     */
    public static URL buildTopStoriesURL()
    {
        URL url = null;

        Uri uri = Uri.parse(API_URL).buildUpon()
                .appendPath("v0")
                .appendPath("topstories.json")
                .build();

        try
        {
            url = new URL(uri.toString());
        }
        catch (Exception ex)
        {
            Log.e("URL Construction Error","The URL failed to build in the try/catch in buildTopStoriesURL");
            ex.printStackTrace();
        }

        return url;
    }

    /**
     * Constructs the per-story API url
     * @param storyId the story ID to access as a string
     * @return the story API url as a URL
     */
    public static URL buildStoryURL(String storyId)
    {
        URL url = null;

        Uri uri = Uri.parse(API_URL).buildUpon()
                .appendPath("v0")
                .appendPath("item")
                .appendPath(storyId + ".json")
                .build();

        try
        {
            url = new URL(uri.toString());
        }
        catch (Exception ex)
        {
            Log.e("URL Construction Error","The URL failed to build in the try/catch in buildStoryURL");
            ex.printStackTrace();
        }

        return url;
    }
}
