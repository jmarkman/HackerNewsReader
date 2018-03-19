package com.jmarkman.hackernewsreader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;

public class FrontPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

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
    }

    public class ArticleQueryTask extends AsyncTask<URL, Void, String>
    {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String result = null;

            try
            {
                result = HackerNewsAPI.getJSON(searchURL);
            }
            catch (IOException ex)
            {
                Log.e("ArticleQuery Error", "JSON fetch error in doInBackground");
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result)
        {
            TextView jsonResult = (TextView) findViewById(R.id.json_results);
            jsonResult.setText(result);
        }
    }
}
