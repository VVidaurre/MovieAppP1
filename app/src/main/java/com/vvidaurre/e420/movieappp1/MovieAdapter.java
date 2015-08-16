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
    static class ViewHolder {
        ImageView movieIcon;
    }
    public MovieAdapter(Activity context,List<Movie> movies){
        super(context, 0,movies);
    }
    @Override
        public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        Movie movie = getItem(position);
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.grid_item_movie_imageview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.movieIcon = (ImageView) convertView.findViewById(R.id.grid_item_movie_imageview);
            convertView.setTag(viewHolder);
            try {
                Picasso.with(getContext()).load(movie.mPoster).error(R.drawable.error).into(viewHolder.movieIcon);
            } catch (IllegalArgumentException e) {
                viewHolder.movieIcon.setImageResource(R.drawable.error);
            }
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            Picasso.with(getContext()).load(movie.mPoster).error(R.drawable.error).into(viewHolder.movieIcon);
        } catch (IllegalArgumentException e) {
            viewHolder.movieIcon.setImageResource(R.drawable.error);
        }
            return convertView;
        }
}
