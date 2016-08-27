package com.example.android.popmovies;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import static android.provider.Settings.System.AIRPLANE_MODE_ON;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by happyding3 on 2016/8/22.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    public MovieAdapter(Context context, ArrayList<Movie> mMovie) {
        super(context, 0, mMovie);
    }
//    static boolean isAirplaneModeOn(Context context) {
//        ContentResolver contentResolver = context.getContentResolver();
//        return Settings.System.getInt(contentResolver, AIRPLANE_MODE_ON, 0) != 0;
//    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View itemView=convertView;
        if (itemView==null){
            itemView= LayoutInflater.from(getContext()).inflate(R.layout.item,parent,false);
        }
        final Movie currentMovie=getItem(position);
        ImageView mPoster= (ImageView) itemView.findViewById(R.id.movie_poster);
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185"+currentMovie.getPosterPath()).into(mPoster);
        return itemView;
    }
}
