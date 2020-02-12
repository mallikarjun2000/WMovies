package com.example.android.wmovies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android.wmovies.MainActivity;
import com.example.android.wmovies.Models.MainMovieBody;
import com.example.android.wmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainMoviesAdapter extends ArrayAdapter {
    public MainMoviesAdapter(@NonNull Context context, ArrayList<MainMovieBody> resource) {
        super(context,0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mView = convertView;
        MainMovieBody movie = (MainMovieBody) getItem(position);
        if(mView == null)
        {
            mView = LayoutInflater.from(getContext()).inflate(R.layout.single_grid_layout,parent,false);
        }
        ImageView imageView = mView.findViewById(R.id.main_single_image);
        TextView name = mView.findViewById(R.id.main_single_name);
        TextView rating = mView.findViewById(R.id.main_single_rating);

        name.setText(movie.getName());
        rating.setText(movie.getRating()+"/10");
        Picasso.get().load(movie.getImage_url()).into(imageView);

        return mView;
    }
}
