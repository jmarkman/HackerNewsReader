package com.jmarkman.hackernewsreader;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
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
     * Retrieve the article IDs from the JSON array and store them as strings for later usage
     * @param json the response JSON from the top stories API call
     * @return an array list of type string
     */
    public static ArrayList<String> getStoryIDsFromJSON(String json)
    {
        ArrayList<String> articleIDs = new ArrayList<>();
        try
        {
            JSONArray arrayArticles = new JSONArray(json);
            for (int i = 0; i < arrayArticles.length(); i++)
            {
                articleIDs.add(arrayArticles.getString(i));
            }
        }
        catch (JSONException jse)
        {
            jse.printStackTrace();
        }

        return articleIDs;
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

    // TODO: Make sure that all params returned from JSON always contain information
    /**
     * Fetches the JSON for each article and stores each article in an Article object
     * @param json an ArrayList of strings that represent the JSON for each article
     * @return an ArrayList of Article objects
     */
    public static ArrayList<Article> getArticlesFromJSON(ArrayList<String> json)
    {
        final String TITLE = "title";
        final String SCORE = "score";
        final String ARTICLE_URL = "url";
        final String COMMENTS = "descendants";
        final String DATE = "time";
        final String USER = "by";

        ArrayList<Article> articles = new ArrayList<>();

        try
        {
            int numArticles = json.size();

            for (int i = 0; i < numArticles; i++)
            {
                JSONObject articleJSON = new JSONObject(json.get(i));

                String articleURL;
                String numComments;
                String normalDate;

                if (articleJSON.has(ARTICLE_URL))
                {
                    articleURL = articleJSON.getString(ARTICLE_URL);
                }
                else
                {
                    articleURL = "HN Thread";
                }

                if (articleJSON.has(COMMENTS))
                {
                    numComments = articleJSON.getString(COMMENTS);
                }
                else
                {
                    numComments = "0";
                }

                normalDate = formatDateFromUnix(articleJSON.getString(DATE));


                Article article = new Article(
                        articleJSON.getString(TITLE),
                        articleURL,
                        normalDate,
                        numComments,
                        articleJSON.getString(USER),
                        articleJSON.getString(SCORE)
                );

                articles.add(article);
            }
        }
        catch (JSONException jse)
        {
            jse.printStackTrace();

        }

        return articles;
    }

    /**
     * Converts UNIX timestamp dates to human-readable dates
     * @param jsonDate the UNIX timestamp
     * @return the formatted human-readable date as a string
     */
    private static String formatDateFromUnix(String jsonDate)
    {
        // https://stackoverflow.com/a/17433005
        long unixSeconds = Long.parseLong(jsonDate);
        Date date = new Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String formattedDate = sdf.format(date);

        return formattedDate;
    }
}
