package com.vvidaurre.e420.movieappp1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by E420 on 08/08/2015.
 */
public class MovieAdapter extends ArrayAdapter<Movie>
{

    public MovieAdapter(Activity context,List<Movie> movies){
        super(context, 0,movies);
    }
    @Override
        public View getView(int position, View convertView, ViewGroup parent){

        Movie movie = getItem(position);
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_movie_imageview, parent, false);
        ImageView imageView = (ImageView)rootView.findViewById(R.id.grid_item_movie_imageview);

        Picasso.with(getContext()).load(movie.mPoster).into(imageView);
    return rootView;
        }
}
