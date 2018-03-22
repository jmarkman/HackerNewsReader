package com.jmarkman.hackernewsreader;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable
{
    public String articleTitle;
    public String articleUrl;
    public String submissionTime;
    public String numComments;
    public String submittedBy;
    public String userScore;

    public Article(String articleTitle, String articleUrl, String submissionTime, String numComments, String submittedBy, String userScore)
    {
        this.articleTitle = articleTitle;
        this.articleUrl = articleUrl;
        this.submissionTime = submissionTime;
        this.numComments = numComments;
        this.submittedBy = submittedBy;
        this.userScore = userScore;
    }


    protected Article(Parcel in)
    {
        articleTitle = in.readString();
        articleUrl = in.readString();
        submissionTime = in.readString();
        numComments = in.readString();
        submittedBy = in.readString();
        userScore = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>()
    {
        @Override
        public Article createFromParcel(Parcel in)
        {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size)
        {
            return new Article[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(articleTitle);
        parcel.writeString(articleUrl);
        parcel.writeString(submissionTime);
        parcel.writeString(numComments);
        parcel.writeString(submittedBy);
        parcel.writeString(userScore);
    }
}
