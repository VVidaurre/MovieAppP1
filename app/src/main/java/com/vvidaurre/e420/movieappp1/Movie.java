package com.vvidaurre.e420.movieappp1;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by E420 on 08/08/2015.
 */
public class Movie implements Parcelable{
    String mPoster;
    String mTitle;
    String mOverview;
    String mReleaseDate;
    Double mVoteAverage;

    public int describeContents(){
        return 0;
    }
    public void writeToParcel(Parcel out, int flags){

        out.writeString(mPoster);
        out.writeString(mTitle);
        out.writeString(mOverview);
        out.writeString(mReleaseDate);
        out.writeDouble(mVoteAverage);
    }
    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private Movie(Parcel in) {
        this.mPoster= in.readString();
        this.mTitle= in.readString();
        this.mOverview= in.readString();
        this.mReleaseDate= in.readString();
        this.mVoteAverage = in.readDouble();
    }
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
