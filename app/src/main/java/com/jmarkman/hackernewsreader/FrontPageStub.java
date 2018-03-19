package com.jmarkman.hackernewsreader;

import android.os.Parcel;
import android.os.Parcelable;

public class FrontPageStub implements Parcelable
{
    public String articleTitle;
    public String articleUrl;
    public String submissionTime;
    public String numComments;

    public FrontPageStub(String articleTitle, String articleUrl, String submissionTime, String numComments)
    {
        this.articleTitle = articleTitle;
        this.articleUrl = articleUrl;
        this.submissionTime = submissionTime;
        this.numComments = numComments;
    }


    protected FrontPageStub(Parcel in)
    {
        articleTitle = in.readString();
        articleUrl = in.readString();
        submissionTime = in.readString();
        numComments = in.readString();
    }

    public static final Creator<FrontPageStub> CREATOR = new Creator<FrontPageStub>()
    {
        @Override
        public FrontPageStub createFromParcel(Parcel in)
        {
            return new FrontPageStub(in);
        }

        @Override
        public FrontPageStub[] newArray(int size)
        {
            return new FrontPageStub[size];
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
    }
}
