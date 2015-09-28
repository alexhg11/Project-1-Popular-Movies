package com.alexhdz.popularmovies.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.alexhdz.popularmovies.R;
import com.alexhdz.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ImageAdapter extends BaseAdapter {

    // Properties
    private Context mContext;
    private List<Movie> movies;

    // Basic Constructor
    public ImageAdapter(Context context, List<Movie> movies) {
        this.mContext = context;
        this.movies = movies;
    }

    // Getters & Setters
    public List<Movie> getMovies() {
        return movies;
    }
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    // Interface Methods
    public int getCount() {
        return movies != null ? movies.size() : 0;
    }

    public Movie getItem(int position) {
        return movies.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(342, 513));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2, 2, 2, 2);
        } else {
            imageView = (ImageView) convertView;
        }

        StringBuilder url = new StringBuilder();

        final String base = "http://image.tmdb.org/t/p/w342/";

        try {
            Movie current = movies.get(position);
            url.append(base);
            url.append(current.getPosterUri());
        } catch ( IndexOutOfBoundsException e ) {
            url.append("android.resource://com.alexhdz.popularmovies/");
            url.append(R.drawable.not_available);
        }

        Picasso.with(mContext).load(url.toString()).into(imageView);

        return imageView;
    }
}