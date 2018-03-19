package com.jmarkman.hackernewsreader;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

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

    /**
     * Fetches the JSON results for the URL
     * @param url - the API url as a URL
     * @return - the JSON results as a String
     * @throws IOException
     */
    public static String getJSON(URL url) throws IOException
    {
        // Establish connection to API
        HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
        // Actually read the data from the API
        InputStream stream = conn.getInputStream();
        // Use a scanner to read the contents retrieved from the stream and parse it
        Scanner scan = new Scanner(stream);
        scan.useDelimiter("\\A");

        try
        {
            if (scan.hasNext())
            {
                return scan.next();
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            Log.e("JSON Fetch Error","Something went wrong while fetching JSON in getJSON");
            return null;
        }
        finally
        {
            // Regardless of the result, make sure that the connection
            // to the API is severed to prevent various errors and leaks
            conn.disconnect();
        }
    }
}
