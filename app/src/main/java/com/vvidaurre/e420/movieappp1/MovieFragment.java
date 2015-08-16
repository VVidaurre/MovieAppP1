package com.vvidaurre.e420.movieappp1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MovieFragment extends Fragment {

    private MovieAdapter mMovieAdapter;
    private static final String POSTER_EXTRA = "POSTER_EXTRA";
    private static final String TITLE_EXTRA = "TITLE_EXTRA";
    private static final String OVERVIEW_EXTRA = "OVERVIEW_EXTRA";
    private static final String RELEASE_EXTRA = "RELEASE_EXTRA";
    private static final String VOTE_EXTRA = "VOTE_EXTRA";
    private String mSort ="";
    private  Toast mToast;
    public MovieFragment() {
    }
    @Override
    public void onStart(){
        super.onStart();
        new getPopularMovies().execute(mSort);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void showToast(CharSequence toastText)
    {
        Context context = getActivity();
        CharSequence text = toastText;
        int duration = Toast.LENGTH_LONG;
    if(mToast != null)
    {
        mToast.cancel();
    }
       mToast = Toast.makeText(context, text, duration);
        mToast.show();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("mSort", mSort);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       if(savedInstanceState!=null && savedInstanceState.containsKey("mSort"))
        mSort = savedInstanceState.getString("mSort");
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.moviefragment, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_spinner_sort);
        View view = menuItem.getActionView(); // find the spinner

        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sort_options_array, android.R.layout.simple_spinner_dropdown_item);

        if(view instanceof Spinner)
        {
            final Spinner spinner = (Spinner)view;
            spinner.setAdapter(mSpinnerAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   String selectedSort = String.valueOf(parent.getItemAtPosition(position));
                    switch (selectedSort)
                    {
                        case "Most Popular":
                            mSort= "popularity.desc";
                            break;
                        case "Highest Rated":
                            mSort= "vote_count.desc";
                            break;
                        default:
                            mSort= "popularity.desc";
                            break;
                    }
                    GridView gridView = (GridView)getActivity().findViewById(R.id.gridview_moviegrid);
                    gridView.smoothScrollToPosition(0);
                    new getPopularMovies().execute(mSort);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
         View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
       ArrayList<Movie> listMovies = new ArrayList<Movie>();
        mMovieAdapter = new MovieAdapter(getActivity(), listMovies);
        GridView gridView = (GridView)rootView.findViewById(R.id.gridview_moviegrid);
        gridView.setAdapter(mMovieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = mMovieAdapter.getItem(position);
                Intent detailIntent = new Intent(getActivity(),DetailActivity.class)
                        .putExtra(POSTER_EXTRA,movie.mPoster)
                        .putExtra(TITLE_EXTRA, movie.mTitle)
                        .putExtra(RELEASE_EXTRA, movie.mReleaseDate)
                        .putExtra(VOTE_EXTRA, movie.mVoteAverage)
                        .putExtra(OVERVIEW_EXTRA, movie.mOverview);
                startActivity(detailIntent);

            }
        });
        return rootView;
    }
    public class getPopularMovies extends AsyncTask<String,Void,Movie[]>
    {
        private final String LOG_TAG = getPopularMovies.class.getSimpleName();

        @Override
        protected void onPostExecute(Movie[] movies) {
            if(movies!=null) {
            mMovieAdapter.clear();
                for (Movie movie : movies) {
                    mMovieAdapter.add(movie);
                }
                super.onPostExecute(movies);
            }
            else
            {
                showToast("Error loading Movies!");
            }
        }
        @Override
        protected Movie[] doInBackground(String... strings){
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieDBJsonStr = null;
            String sort = "popularity.desc";
            String MOVIEDB_SORT = "sort_by";
            if(strings.length > 0){
                sort= strings[0];
            }
            try
            {
                final String MOVIEDB_BASE_URL = "https://api.themoviedb.org/3/discover/movie?api_key=94dd479045acd372f898c94c5998317e";
                Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                        .appendQueryParameter(MOVIEDB_SORT,sort)
                        .build();
                URL url = new URL(builtUri.toString());
             urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream==null)
                {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = reader.readLine())!= null){
                    buffer.append(line  +"\n");
                }
                if (buffer.length()==0)
                {
                    return null;
                }
                movieDBJsonStr = buffer.toString();
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString("MOVIE_DATA", movieDBJsonStr).commit();
               return getMovieDBDataFromJson(movieDBJsonStr);
            }
            catch (JSONException e)
            {
                Log.e(LOG_TAG, "Json Error", e);
                return null;
            }
            catch (IOException e)
            {
                Log.e(LOG_TAG, "Error", e);
                return null;
            }
            finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                if(reader != null)
                {
                    try {
                        reader.close();
                    }
                    catch (final IOException e){
                        Log.e(LOG_TAG,"Error closing stream",e);
                    }
                }
            }
        }
        private Movie[] getMovieDBDataFromJson(String movieDBJsonStr)
        throws JSONException{
            final String MOVIEDB_RESULTS= "results";
            final String MOVIEDB_POSTER="poster_path";
            final String MOVIEDB_TITLE="title";
            final String MOVIEDB_RELEASE_DATE="release_date";
            final String MOVIEDB_VOTE_AVG="vote_average";
            final String MOVIEDB_OVERVIEW="overview";
            final String MOVIEDB_POPULARITY="popularity";

            JSONObject movieDBJson = new JSONObject(movieDBJsonStr);
            JSONArray movieDBArray = movieDBJson.getJSONArray(MOVIEDB_RESULTS);
            Movie[] resultMovies = new Movie[movieDBArray.length()];
            for(int i = 0; i < movieDBArray.length(); i++){
                JSONObject movieDBJsonObj = movieDBArray.getJSONObject(i);
                resultMovies[i] = new Movie(movieDBJsonObj.getString(MOVIEDB_POSTER),
                                            movieDBJsonObj.getString(MOVIEDB_TITLE),
                                            movieDBJsonObj.getString(MOVIEDB_OVERVIEW),
                        returnFormattedDate(movieDBJsonObj.getString(MOVIEDB_RELEASE_DATE)),
                        movieDBJsonObj.getDouble(MOVIEDB_VOTE_AVG));

            }
        return resultMovies;
        }
        public String returnFormattedDate(String date){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date rDate = new Date();
            try
            {
                rDate = dateFormat.parse(date);
               // java.text.DateFormat localizedDateF = DateFormat.getDateFormat(getActivity());
                dateFormat = new SimpleDateFormat("yyyy");
                String formattedDate = dateFormat.format(rDate);
            return formattedDate;
            }
            catch (ParseException e)
            {
                Log.e(LOG_TAG,"Error Formatting date:" + e.getMessage());
                return "Unable to Parse Date";
            }
        }
    }



}
