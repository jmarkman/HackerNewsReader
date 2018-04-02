package com.jmarkman.hackernewsreader;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener
{
    // The minimum amount of items to have below the current scroll position before loading more
    private int visibleThreshold = 5;
    // The current offset index of data loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data after load
    private boolean loading = true;
    // Set the starting page index
    private int startingPageIndex = 0;

    RecyclerView.LayoutManager layoutManager;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layMan)
    {
        layoutManager = layMan;
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager layMan)
    {
        layoutManager = layMan;
        visibleThreshold = visibleThreshold * layMan.getSpanCount();
    }

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layMan)
    {
        layoutManager = layMan;
        visibleThreshold = visibleThreshold * layMan.getSpanCount();
    }
}
