package com.jmarkman.hackernewsreader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>
{
    ArrayList<Article> articles;

    public ArticleAdapter(ArrayList<Article> articles)
    {
        this.articles = articles;
    }

    @Override
    public ArticleAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.front_page_stub, parent, false);

        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ArticleViewHolder holder, int position)
    {
        Article article = articles.get(position);
        holder.bind(article);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvTitle;
        TextView tvSource;
        TextView tvDate;
        TextView tvSubmittedBy;
        TextView tvUserScore;
        TextView tvComments;

        public ArticleViewHolder(View itemView)
        {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.stub_article_title);
            tvSource = itemView.findViewById(R.id.stub_article_source);
            tvDate = itemView.findViewById(R.id.stub_article_date);
            tvSubmittedBy = itemView.findViewById(R.id.stub_submitter);
            tvUserScore = itemView.findViewById(R.id.stub_article_score);
            tvComments = itemView.findViewById(R.id.stub_article_comments);
            itemView.setOnClickListener(this);
        }

        public void bind(Article article)
        {
            tvTitle.setText(article.articleTitle);
            tvSource.setText(article.articleUrl);
            tvDate.setText(article.submissionTime);
            tvSubmittedBy.setText(article.submittedBy);
            tvUserScore.setText(article.userScore);
            tvComments.setText(article.numComments);
        }

        @Override
        public void onClick(View view)
        {
            return;
        }
    }
}
