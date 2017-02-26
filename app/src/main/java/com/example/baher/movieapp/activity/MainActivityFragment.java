package com.example.baher.movieapp.activity;

/**
 * Created by baher on 9/19/16.
 */


import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baher.movieapp.R;
import com.example.baher.movieapp.adapters.MoviesAdapter;
import com.example.baher.movieapp.data.ApiBase;
import com.example.baher.movieapp.data.RestApi;
import com.example.baher.movieapp.models.Movie;
import com.example.baher.movieapp.models.Movies;
import com.example.baher.movieapp.models.Trailer;
import com.example.baher.movieapp.models.TrailerArray;
import com.example.baher.movieapp.models.review;
import com.example.baher.movieapp.models.reviewsarray;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment containing the list view of Android versions.
 */
public class MainActivityFragment extends Fragment {
    public static int favnums;
    int position;
    private final static String API_KEY = "28c111e7dac7ba08b14053325707ee11";
    Parcelable state;
    TextView MovieTitle;
    TextView MovieOverview;
    TextView MovieaverageVote;
    TextView Moviedate;
    ImageView movieposter;
    List<Trailer> trailers;
    ImageButton favourite;
    List<review> reviews;
    String key;
    SharedPreferences mPrefs;
    MoviesAdapter moviesAdapter;
    List<Movie> movies;
    List<Movie> favmovies;
     Intent intent;
    GridView gridView;


    public MainActivityFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

      intent =getActivity().getIntent();

        try {
            gridView = (GridView) rootView.findViewById(R.id.movies_grid);
            mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            favmovies = new ArrayList<Movie>();

            if (savedInstanceState != null)
                position = (int) savedInstanceState.get("position");
            if (position != gridView.INVALID_POSITION) {
                gridView.setSelection(position);
            }
            RestApi apiService =
                    ApiBase.getClient().create(RestApi.class);
            if (MainActivity.getType() == 0) {
                Call<Movies> call = apiService.getMostPoupularMovies(API_KEY);
                call.enqueue(new Callback<Movies>() {
                    @Override
                    public void onResponse(Call<Movies> call, Response<Movies> response) {
                        movies = response.body().getResults();
                        moviesAdapter = new MoviesAdapter(getActivity(), movies);
                        moviesAdapter.notifyDataSetChanged();
                        gridView.setAdapter(moviesAdapter);


                    }

                    @Override
                    public void onFailure(Call<Movies> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("error", t.toString());
                    }
                });
            } else {
                if (MainActivity.getType() == 1) {
                    Call<Movies> call = apiService.getTopRatedMovies(API_KEY);
                    call.enqueue(new Callback<Movies>() {
                        @Override
                        public void onResponse(Call<Movies> call, Response<Movies> response) {
                            movies = response.body().getResults();
                            if (movies != null) {
                                moviesAdapter = new MoviesAdapter(getActivity(), movies);
                                moviesAdapter.notifyDataSetChanged();

                                gridView.setAdapter(moviesAdapter);


                            }
                        }

                        @Override
                        public void onFailure(Call<Movies> call, Throwable t) {
                            // Log error here since request failed
                            Log.e("error", t.toString());
                        }
                    });
                } else {
                    int i = 0;
                    boolean exist = false;
                    while (mPrefs.getString("MyObject" + i, null) != null) {
                        Gson gson = new Gson();
                        String json = mPrefs.getString("MyObject" + i, "");
                        Movie obj = gson.fromJson(json, Movie.class);
                        for (int k = 0; k < favmovies.size(); k++) {
                            if (favmovies.get(k).getId().equals(obj.getId())) {
                                exist = true;

                            }
                        }
                        if (!exist)
                            favmovies.add(obj);

                        exist = false;

                        i++;
                    }
                    if (favmovies != null) {
                        moviesAdapter = new MoviesAdapter(getActivity(), favmovies);
                        moviesAdapter.notifyDataSetChanged();
                        gridView.setAdapter(moviesAdapter);
                    }
                }
            }


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    View detailFrame = getActivity().findViewById(R.id.movies_detail);
                    boolean isDual = detailFrame != null && detailFrame.getVisibility() == View.VISIBLE;
                    if (isDual) {
                        mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                        MovieTitle = (TextView) (getActivity().findViewById(R.id.moviename));
                        favourite = (ImageButton) (getActivity().findViewById(R.id.markasfavourite));
                        MovieOverview = (TextView) (getActivity().findViewById(R.id.movieoverview));
                        Moviedate = (TextView) (getActivity().findViewById(R.id.moviedate));
                        MovieaverageVote = (TextView) (getActivity().findViewById(R.id.movierate));
                        movieposter = (ImageView) (getActivity().findViewById(R.id.detail_image));
                        final String title;
                        final String overview;
                        final String date;
                        final String posterpath;
                        final String vote;
                        final String movieid;
                        if (movies != null) {
                            title = movies.get(position).getOriginalTitle();
                            overview = movies.get(position).getOverview();
                            date = movies.get(position).getReleaseDate();
                            posterpath = movies.get(position).getPosterPath();

                            vote = movies.get(position).getVote_average();
                            movieid = movies.get(position).getId();
                        } else {
                            title = favmovies.get(position).getOriginalTitle();
                            overview = favmovies.get(position).getOverview();
                            date = favmovies.get(position).getReleaseDate();
                            posterpath = favmovies.get(position).getPosterPath();

                            vote = favmovies.get(position).getVote_average();
                            movieid = favmovies.get(position).getId();
                        }
                        MovieTitle.setText(title);
                        MovieOverview.setText(overview);
                        Moviedate.setText(date);
                        MovieaverageVote.setText(vote + "/10");
                        favourite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Movie movie = new Movie(posterpath, false, overview, date, null, movieid, title, null, title, null, 0, 0, false, vote);
                                int i = 0;

                                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                while (mPrefs.getString("MyObject" + i, null) != null) {
                                    i++;
                                }
                                Gson gson = new Gson();
                                String json = gson.toJson(movie);
                                prefsEditor.putString("MyObject" + i, json);
                                prefsEditor.commit();
                                favourite.setImageResource(R.drawable.favourite);
                            }
                        });

                        final LinearLayout trailers_linear_layout = (LinearLayout) getActivity().findViewById(R.id.trailerarray);
                        final LinearLayout reviews_linear_layout = (LinearLayout) getActivity().findViewById(R.id.reviewarray);

                        if(trailers_linear_layout.getChildCount() > 0)
                            trailers_linear_layout.removeAllViews();
                        if(reviews_linear_layout.getChildCount() > 0)
                            reviews_linear_layout.removeAllViews();
                      ImageButton  favourite = (ImageButton) (getActivity().findViewById(R.id.markasfavourite));
                         favourite.setImageResource(R.drawable.favo);
                        RestApi apiService =
                                ApiBase.getClient().create(RestApi.class);
                        Call<TrailerArray> call = apiService.getMovieDetails(Integer.parseInt(movieid), API_KEY);
                        call.enqueue(new Callback<TrailerArray>() {
                            @Override
                            public void onResponse(Call<TrailerArray> call, Response<TrailerArray> response) {
                                trailers = response.body().getResults();
                                try {


                                    if (trailers != null) {
                                        for (int i = 0; i < trailers.size(); i++) {
                                            View view = LayoutInflater.from(getContext()).inflate(R.layout.trailer_view, null);
                                            view.setId(i);
                                            ImageView imageView = (ImageView) view.findViewById(R.id.playbutton);
                                            key = trailers.get(i).getKey();
                                            String iso = trailers.get(i).getIso_639_1();

                                            imageView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + key)));

                                                }
                                            });
                                            TextView trailernum = (TextView) view.findViewById(R.id.trailernum);
                                            trailernum.setText("trailer" + i);
                                            trailers_linear_layout.addView(view);
                                        }

                                    }
                                } catch (NullPointerException e) {

                                }
                            }

                            @Override
                            public void onFailure(Call<TrailerArray> call, Throwable t) {
                                // Log error here since request failed
                            }
                        });
                        Call<reviewsarray> call1 = apiService.getMoviereviews(Integer.parseInt(movieid), API_KEY);
                        call1.enqueue(new Callback<reviewsarray>() {
                            @Override
                            public void onResponse(Call<reviewsarray> call1, Response<reviewsarray> response) {
                                reviews = response.body().getResults();
                                if (reviews != null) {
                                    for (int i = 0; i < reviews.size(); i++) {
                                        View view = LayoutInflater.from(getContext()).inflate(R.layout.review_view, null);
                                        TextView author = (TextView) view.findViewById(R.id.reviewname);
                                        TextView content = (TextView) view.findViewById(R.id.reviewcontent);
                                        author.setText(reviews.get(i).getAuthor());
                                        content.setText(reviews.get(i).getContent());
                                        reviews_linear_layout.addView(view);
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<reviewsarray> call1, Throwable t) {
                                // Log error here since request failed
                            }
                        });
                        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w500//" + posterpath).into(movieposter, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {

                                Toast.makeText(getContext(), "there was error in loading data", Toast.LENGTH_LONG).show();
                                System.out.print("failed");

                            }
                        });

                    } else {


                        if (movies != null) {
                            Intent i = new Intent(getContext(), MovieDetails.class);
                            i.putExtra("Moviename", movies.get(position).getOriginalTitle());
                            i.putExtra("MovieId", movies.get(position).getId());
                            i.putExtra("Movieoverview", movies.get(position).getOverview());
                            i.putExtra("Movieposterpath", movies.get(position).getPosterPath());
                            i.putExtra("Moviedate", movies.get(position).getReleaseDate());
                            i.putExtra("Movievote", movies.get(position).getVote_average());
                            getContext().startActivity(i);
                        } else {
                            if (favmovies != null) {
                                Intent i = new Intent(getContext(), MovieDetails.class);
                                i.putExtra("Moviename", favmovies.get(position).getOriginalTitle());
                                i.putExtra("MovieId", favmovies.get(position).getId());
                                i.putExtra("Movieoverview", favmovies.get(position).getOverview());
                                i.putExtra("Movieposterpath", favmovies.get(position).getPosterPath());
                                i.putExtra("Moviedate", favmovies.get(position).getReleaseDate());
                                i.putExtra("Movievote", favmovies.get(position).getVote_average());
                                getContext().startActivity(i);
                            }
                        }
                    }
                }
            });
        }catch (NullPointerException e){
            startActivity(intent);
        }
        return rootView;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        position = gridView.getFirstVisiblePosition();
        outState.putInt("position",position);
    }
    @Override
   public void onResume() {
        super.onResume();
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState!=null)
            position = (int) savedInstanceState.get("position");
        else
            position = 0;
    }

    @Override
    public void onPause() {
        // Save ListView state @ onPause
        state = gridView.onSaveInstanceState();
        super.onPause();
    }

}

