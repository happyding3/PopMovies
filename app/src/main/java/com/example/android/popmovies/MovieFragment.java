package com.example.android.popmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MovieFragment extends Fragment {
    public MovieAdapter mMovieAdapter;

    public MovieFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView;
        rootView = inflater.inflate(R.layout.fragment_main,container,false);
        mMovieAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());
        final GridView gridView = (GridView)rootView.findViewById(R.id.gridView);
        gridView.setAdapter(mMovieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie movie=mMovieAdapter.getItem(i);
                Log.i("mMovieAdapter.getItem",movie.getAll());
                Intent intent=new Intent(getActivity(),DetailActivity.class).putExtra("movie", movie);
                startActivity(intent);

            }}
        );
        return rootView;

    }

    public void onStart(){
        super.onStart();
        new FetchMovieTask().execute();
    }





    class FetchMovieTask extends AsyncTask<Void, Void, ArrayList<Movie>> {
        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();


        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {
//                if (params.length == 0) {
//                    return null;
//                }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;
            
            try {
                final String MOVIE_BASE_URL="http://api.themoviedb.org/3/movie/";
                String SORT_BY=null;
                if (PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString("sort","Popular").equals("Popular"))
                {SORT_BY="popular";
                }else if(PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .getString("sort","Popular").equals("Top rated")){SORT_BY="top_rated";
                }else{Log.d("sort","sort by not found:"+
                        PreferenceManager.getDefaultSharedPreferences(getActivity())
                                .getString("sort","Popular"));}
//                Uri builtUri=Uri.parse(MOVIE_BASE_URL).buildUpon()
//                        .appendQueryParameter(SORT_BY, format).build();
                URL url = new URL(MOVIE_BASE_URL+SORT_BY+"?api_key=MY_API");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        movieJsonStr = null;
                    }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                int i = 1;
                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line);
                    Log.v(LOG_TAG, i + "xyz:" + line);
                    i++;
                }

                    if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    movieJsonStr = null;
                }
                movieJsonStr = buffer.toString();
                Log.v(LOG_TAG, "Movie JSON String:" + movieJsonStr);
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the Movie data, there's no point in attempting
                // to parse it.
                movieJsonStr = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }


                }

            }
            try {

                return  getMovieDataFromeJson(movieJsonStr);

            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(ArrayList<Movie> S) {
            super.onPostExecute(S);
//            TextView mText = (TextView) findViewById(R.id.text);
//            mText.setText(s);
//            try {
//                getMovieDataFromeJson(s);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            if (S!=null){
               mMovieAdapter.clear();
                for (Movie movie:S){
                    mMovieAdapter.add(movie);
                    Log.i("movie",movie.getOverView());
                }

            }
        }

        private ArrayList<Movie> getMovieDataFromeJson(String movieJsonStr)
                throws JSONException {

            final String RESULTS = "results";
            final String TITLE = "title";
            final String POSTER_PATH = "poster_path";
            final String OVERVIEW = "overview";
            final String VOTE_AVERAGE = "vote_average";
            final String RELEASE_DATE = "release_date";

            ArrayList<Movie> mMovie=new ArrayList<Movie>();
            final String BASEURL = "http://image.tmdb.org/t/p/";
            final String SIZE = "w185";
            JSONArray movieArray;
            JSONObject movieJson = new JSONObject(movieJsonStr);
            movieArray = movieJson.getJSONArray(RESULTS);
            //ArrayList<Movie> mMovie = new ArrayList<>();
            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movie = movieArray.getJSONObject(i);
                String title = movie.getString(TITLE);
                String posterPath = movie.getString(POSTER_PATH);
                String overView = movie.getString(OVERVIEW);
                String voteAverage = movie.getString(VOTE_AVERAGE);
                String releaseDate = movie.getString(RELEASE_DATE);

                mMovie.add(new Movie(title, posterPath, overView, voteAverage, releaseDate));
                Log.i("mMovie", mMovie.get(i).getAll());

            }


            return mMovie;
    }
}}


