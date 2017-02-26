package com.example.baher.movieapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.baher.movieapp.R;
import com.example.baher.movieapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by baher on 9/20/16.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {

    public MoviesAdapter(Activity context, List<Movie> MoviesList) {

        super(context, 0, MoviesList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position

            Movie movie = getItem(position);

            // Adapters recycle views to AdapterViews.
            // If this is a new View object we're getting, then inflate the layout.
            // If not, this view already has the layout inflated from a previous call to getView,
            // and we modify the View widgets as usual.
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.movie_item, parent, false);
            }
            ImageView iconView = (ImageView) convertView.findViewById(R.id.movie_image);
            if (movie != null) {
                Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185//" + movie.getPosterPath()).into(iconView);
            }


            return convertView;
        }
}

