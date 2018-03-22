package com.jmarkman.hackernewsreader;

import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MiscUnitTest
{
    @Test
    public void dateFormat_isCorrect() throws Exception
    {
        String hello = "Hello!";
        System.out.println(hello);
        assertEquals(hello, "Hello!");
    }

    @Test
    public void articleJSON_isCorrect() throws Exception
    {
        String articleID = "16643056";
        URL url = HackerNewsAPI.buildStoryURL(articleID);

        ArrayList<String> articleList = new ArrayList<>();
        articleList.add(HackerNewsAPI.getJSON(url));

        ArrayList<Article> articles = HackerNewsAPI.getArticlesFromJSON(articleList);
        Article resultArticle = articles.get(0);

        assertEquals(resultArticle.articleTitle, "Tempe Police Release Video of Uber Accident");
        assertEquals(resultArticle.articleUrl, "https://twitter.com/tempepolice/status/976585098542833664?s=21");
        assertEquals(resultArticle.submittedBy, "austinkhale");
    }
}
