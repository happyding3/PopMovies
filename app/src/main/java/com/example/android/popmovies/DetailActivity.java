package com.example.android.popmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    TextView mTitle;
    ImageView mPoster;
    TextView mReleaseDate;
    TextView mVoteRate;
    TextView mOverView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Movie movie=getIntent().getParcelableExtra("movie");
        mTitle= (TextView) findViewById(R.id.title);
        mPoster= (ImageView) findViewById(R.id.poster);
        mReleaseDate=(TextView)findViewById(R.id.releaseDate);
        mVoteRate= (TextView) findViewById(R.id.voteRate);
        mOverView= (TextView) findViewById(R.id.overView);
        Log.i("movie",movie.getAll());
        if (movie!=null){
            mTitle.setText(movie.title);
            Picasso.with(this).load("http://image.tmdb.org/t/p/w185"+movie.posterPath).into(mPoster);
            mReleaseDate.setText(movie.releaseDate);
            mVoteRate.setText(movie.voteAverage+"/10");
            mOverView.setText(movie.overView);
        }
    }
}
