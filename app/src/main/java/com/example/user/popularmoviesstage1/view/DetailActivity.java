package com.example.user.popularmoviesstage1.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.popularmoviesstage1.R;
import com.example.user.popularmoviesstage1.api.APIClient;
import com.example.user.popularmoviesstage1.api.APIMethods;
import com.example.user.popularmoviesstage1.model.Constants;
import com.example.user.popularmoviesstage1.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private String id;
    private ImageView imagePlot;
    private TextView txtTitle;
    private TextView txtDate;
    private TextView txtVote;
    private TextView txtSynopsis;
    private Context context;
    private Movie movie;
    public List<Movie> data;
    private APIMethods apiMethods;
    private String activityTitle = "Popular Movies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        apiMethods = APIClient.getClient().create(APIMethods.class);
        txtTitle = findViewById(R.id.txtTitle);
        txtDate = findViewById(R.id.txtReleaseDate);
        txtVote = findViewById(R.id.txtAverageVote);
        txtSynopsis = findViewById(R.id.txtSynopsis);

        Bundle extras = getIntent().getExtras();

        id = String.valueOf(extras.getInt("id"));
        activityTitle = extras.getString("title");
        getSupportActionBar().setTitle(activityTitle);
        loadData(id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadData(String id) {
        this.id = id;
        Call<Movie> call = apiMethods.getMovie(id, Constants.API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    movie = response.body();
                    txtTitle.setText(movie.getTitle());
                    txtDate.setText(movie.getReleaseDate());
                    txtVote.setText(String.valueOf(movie.getVoteAverage()) + "/10");
                    txtSynopsis.setText(movie.getOverview());
                    activityTitle = movie.getTitle();

                    if (movie.getPosterPath() != null) {
                        imagePlot = findViewById(R.id.image_poster);
                        Picasso.with(context)
                                .load(Constants.API_POSTER_HEADER_LARGE
                                        + movie.getPosterPath())
                                .into(imagePlot);
                    } else {
                        Toast.makeText(getApplicationContext(), "Nothing found, try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No network connection!!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
