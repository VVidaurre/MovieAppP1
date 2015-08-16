package com.vvidaurre.e420.movieappp1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,new DetailFragment())
                    .commit();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static class DetailFragment extends Fragment{
        private static final String POSTER_EXTRA = "POSTER_EXTRA";
        private static final String TITLE_EXTRA = "TITLE_EXTRA";
        private static final String OVERVIEW_EXTRA = "OVERVIEW_EXTRA";
        private static final String RELEASE_EXTRA = "RELEASE_EXTRA";
        private static final String VOTE_EXTRA = "VOTE_EXTRA";
        private String mPoster;
        private String mTitle;
        private String mOverview;
        private String mReleaseDate;
        private Double mVoteAverage;
        public  DetailFragment(){

        }
        @Override public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
            View rootView = inflater.inflate(R.layout.fragment_detail,container,false);
            Intent intent = getActivity().getIntent();
            if (intent!= null && intent.hasExtra(POSTER_EXTRA) && intent.hasExtra(TITLE_EXTRA) && intent.hasExtra(OVERVIEW_EXTRA)
                    && intent.hasExtra(RELEASE_EXTRA) && intent.hasExtra(VOTE_EXTRA)){
                mPoster = intent.getStringExtra(POSTER_EXTRA);
                mTitle = intent.getStringExtra(TITLE_EXTRA);
                mOverview = intent.getStringExtra(OVERVIEW_EXTRA);
                mReleaseDate = intent.getStringExtra(RELEASE_EXTRA);
                mVoteAverage = intent.getDoubleExtra(VOTE_EXTRA, 0);

                ((TextView)rootView.findViewById(R.id.movie_detail_title_textview)).setText(mTitle);
               ImageView imageView =(ImageView) rootView.findViewById(R.id.movie_detail_poster_imageview);
                Picasso.with(getActivity().getApplicationContext()).load(mPoster).into(imageView);
                ((TextView)rootView.findViewById(R.id.movie_detail_rdate_textview)).setText(mReleaseDate);
                ((TextView)rootView.findViewById(R.id.movie_detail_vote_avg_textview)).setText(String.valueOf(mVoteAverage) +"/10");
                ((TextView)rootView.findViewById(R.id.movie_detail_overview_textview)).setText(mOverview);
            }
            return rootView;
        }

    }
}
