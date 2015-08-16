package com.vvidaurre.e420.movieappp1;

import android.net.Uri;

/**
 * Created by E420 on 08/08/2015.
 */
public class Movie {
    String mPoster;
    String mTitle;
    String mOverview;
    String mReleaseDate;
    Double mVoteAverage;
    public Movie(String poster,String title,String overview,String releaseDate,Double voteAverage)
    {
        final String LOG_TAG = MovieAdapter.class.getSimpleName();
        final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
        final String BASE_IMAGE_SCR_SIZE = "w185";
        Uri uri = Uri.parse(BASE_IMAGE_URL).buildUpon()
                .appendPath(BASE_IMAGE_SCR_SIZE)
                .appendEncodedPath(poster)
                .build();
        this.mPoster= uri.toString();
        this.mTitle= title;
        this.mOverview= overview;
        this.mReleaseDate= releaseDate;
        this.mVoteAverage = voteAverage;
    }
}
