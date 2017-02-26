package com.example.baher.movieapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baher.movieapp.R;
import com.example.baher.movieapp.data.ApiBase;
import com.example.baher.movieapp.data.RestApi;
import com.example.baher.movieapp.models.Movie;
import com.example.baher.movieapp.models.Trailer;
import com.example.baher.movieapp.models.TrailerArray;
import com.example.baher.movieapp.models.review;
import com.example.baher.movieapp.models.reviewsarray;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailFragment extends Fragment {
    int position;
    SharedPreferences mPrefs;
    TextView MovieTitle;
    TextView MovieOverview;
    TextView MovieaverageVote;
    TextView Moviedate;
    ImageView movieposter;
    List<Trailer> trailers;
   ImageButton favourite;
    List<review> reviews;
    Intent intent;
     String key;
    private final static String API_KEY = "28c111e7dac7ba08b14053325707ee11";




    public MovieDetailFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(String param1, String param2) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      intent=getActivity().getIntent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // Inflate the layout for this
        try {
            Intent intent = getActivity().getIntent();
            mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            MovieTitle = (TextView) (rootView.findViewById(R.id.moviename));
            favourite = (ImageButton) (rootView.findViewById(R.id.markasfavourite));
            MovieOverview = (TextView) (rootView.findViewById(R.id.movieoverview));
            Moviedate = (TextView) (rootView.findViewById(R.id.moviedate));
            MovieaverageVote = (TextView) (rootView.findViewById(R.id.movierate));
            movieposter = (ImageView) (rootView.findViewById(R.id.detail_image));
            if (intent.getExtras() != null) {
                final String title = intent.getStringExtra("Moviename");
                final String overview = intent.getStringExtra("Movieoverview");
                final String date = intent.getStringExtra("Moviedate");
                final String posterpath = intent.getStringExtra("Movieposterpath");
                final String vote = intent.getStringExtra("Movievote");
                final String id = intent.getStringExtra("MovieId");
                int l=0;
                while(mPrefs.getString("MyObject"+l,null)!=null) {
                    Gson gson = new Gson();
                    String json = mPrefs.getString("MyObject" + l, "");
                    Movie obj = gson.fromJson(json, Movie.class);
                    if(obj.getId().equals(id)){
                        favourite.setImageResource(R.drawable.favourite);
                    }
                    l++;
                }
                MovieTitle.setText(title);
                MovieOverview.setText(overview);
                Moviedate.setText(date);
                MovieaverageVote.setText(vote + "/10");
                favourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Movie movie = new Movie(posterpath, false, overview, date, null, id, title, null, title, null, 0, 0, false, vote);
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

                final LinearLayout trailers_linear_layout = (LinearLayout) rootView.findViewById(R.id.trailerarray);
                final LinearLayout reviews_linear_layout = (LinearLayout) rootView.findViewById(R.id.reviewarray);

                RestApi apiService =
                        ApiBase.getClient().create(RestApi.class);
                Call<TrailerArray> call = apiService.getMovieDetails(Integer.parseInt(id), API_KEY);
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

                Call<reviewsarray> call1 = apiService.getMoviereviews(Integer.parseInt(id), API_KEY);
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
                        MovieDetails.dismiss();
                    }

                    @Override
                    public void onError() {
                        MovieDetails.dismiss();
                        Toast.makeText(getContext(), "there was error in loading data", Toast.LENGTH_LONG).show();

                    }
                });
            }
        }catch (NullPointerException e){
             startActivity(intent);
        }
        return rootView;

    }




}
