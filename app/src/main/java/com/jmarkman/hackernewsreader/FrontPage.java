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
import java.net.URL;
import java.util.ArrayList;

public class FrontPage extends AppCompatActivity
{

    private ProgressBar loadingProgress;
    private RecyclerView rvArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        rvArticles = findViewById(R.id.front_page_articles);
        loadingProgress = findViewById(R.id.front_page_progress);

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

        LinearLayoutManager articlesLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvArticles.setLayoutManager(articlesLayoutManager);
    }

    public class ArticleQueryTask extends AsyncTask<URL, Void, ArrayList<String>>
    {
        @Override
        protected ArrayList<String> doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String articleIDJSON = null; // variable for storing article ID JSON
            ArrayList<String> result = null; // variable for storing story response JSON

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
            //TextView jsonResult = findViewById(R.id.json_results);
            //jsonResult.setText(result);
            TextView tvError = findViewById(R.id.front_page_error);
            loadingProgress.setVisibility(View.INVISIBLE);
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

            ArrayList<Article> articles = HackerNewsAPI.getArticlesFromJSON(result);
            ArticleAdapter adapter = new ArticleAdapter(articles);

            rvArticles.setAdapter(adapter);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            loadingProgress.setVisibility(View.VISIBLE);
        }
    }
}
