package com.jmarkman.hackernewsreader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class FrontPage extends AppCompatActivity
{
    private ProgressBar loadingProgress;
    private RecyclerView rvArticles;
    private EndlessRecyclerViewScrollListener scrollListener;
    private ArrayList<Article> allArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        rvArticles = findViewById(R.id.rv_front_page_articles);
        loadingProgress = findViewById(R.id.front_page_progress);
        scrollListener = new EndlessRecyclerViewScrollListener()
        {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view)
            {
                loadMoreArticles(page);
            }
        };

        rvArticles.addOnScrollListener(scrollListener);

        try
        {
            URL topStories = HackerNewsAPI.buildTopStoriesURL();
            new ArticleQueryTask().execute(topStories);
        }
        catch (Exception ex)
        {
            Log.e("onCreate Error","Something happened");
            ex.printStackTrace();
        }

        // Create a LayoutManager for the RecyclerView
        // A LayoutManager is responsible for measuring and positioning item views within a
        // RecyclerView as well as determining the policy for when to recycle item views
        // that are no longer visible to the user.
        // https://developer.android.com/reference/android/support/v7/widget/RecyclerView.LayoutManager.html
        LinearLayoutManager articlesLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvArticles.setLayoutManager(articlesLayoutManager);
    }

    private void loadMoreArticles(int offset)
    {

    }

    public class ArticleQueryTask extends AsyncTask<URL, Void, ArrayList<String>>
    {
        @Override
        protected ArrayList<String> doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String articleIDJSON = null; // variable for storing article ID JSON
            ArrayList<String> result = new ArrayList<>(); // variable for storing story response JSON

            try
            {
                // Get the JSON array filled with the article IDs
                articleIDJSON = HackerNewsAPI.getJSON(searchURL);

                // Store the article IDs in an ArrayList to turn them into URLs for API requests
                ArrayList<String> articleIDs = HackerNewsAPI.getStoryIDsFromJSON(articleIDJSON);

                // For each API request in articleIDs, build the API request for each story,
                // retrieve the JSON response as a string, and store the response in an
                // ArrayList to be returned to onPostExecute
                for (String url : articleIDs)
                {
                    URL articleURL = HackerNewsAPI.buildStoryURL(url);
                    String jsonResult = HackerNewsAPI.getJSON(articleURL);
                    result.add(jsonResult);
                }
            }
            catch (IOException ex)
            {
                Log.e("ArticleQuery Error", "JSON error in doInBackground");
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result)
        {
            // Create a reference to the TextView that is used to display errors
            TextView tvError = findViewById(R.id.front_page_error);
            // Make sure that the progress bar is invisible once all tasks are done
            loadingProgress.setVisibility(View.INVISIBLE);

            // Logic for if no results are returned; if no results, display an error
            if (result == null || result.size() == 0)
            {
                rvArticles.setVisibility(View.INVISIBLE);
                tvError.setVisibility(View.VISIBLE);
            }
            else
            {
                rvArticles.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.INVISIBLE);
            }

            // Get the article data from the JSON, put the data for each article in
            // an article object, and then put that object in an ArrayList of Articles
            // Then, use that ArrayList as the source for our ArticleAdapter, and
            // finally set the RecyclerView to use that adapter
            allArticles = HackerNewsAPI.getArticlesFromJSON(result);
            ArrayList<Article> articleGroup = new ArrayList<>();
            for (int i = 0; i < 9; i++)
            {
                articleGroup.add(allArticles.get(i));
            }
            ArticleAdapter adapter = new ArticleAdapter(articleGroup);

            rvArticles.setAdapter(adapter);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            // Display the progress bar during execution
            loadingProgress.setVisibility(View.VISIBLE);
        }
    }
}
