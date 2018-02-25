package com.example.user.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.user.popularmoviesstage1.api.APIClient;
import com.example.user.popularmoviesstage1.api.APIMethods;
import com.example.user.popularmoviesstage1.model.MovieList;
import com.example.user.popularmoviesstage1.model.Constants;
import com.example.user.popularmoviesstage1.model.Movie;
import com.example.user.popularmoviesstage1.view.DetailActivity;
import com.example.user.popularmoviesstage1.view.MyClickListener;
import com.example.user.popularmoviesstage1.view.RecyclerViewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    public List<Movie> data;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private Context context;
    private GridLayoutManager layoutManager;
    private APIMethods apiMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiMethods = APIClient.getClient().create(APIMethods.class);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnItemTouchListener(
                new MyClickListener(context, recyclerView, new MyClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        int id = data.get(position).getId();
                        String title = data.get(position).getTitle();

                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("id", id);
                        //Pass movie tile for instant DetailActivity title change
                        intent.putExtra("title", title);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );

        //On initial launch movies sorted by popularity
        loadData("popular");

    }

    //Fetch data from server
    private void loadData(String sortBuy) {
        Call<MovieList> call = apiMethods.getMovieList(sortBuy, Constants.API_KEY);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.isSuccessful() && response.message().equals("OK")) {
                    data = response.body().getResults();
                    if (data.size() != 0) {
                        adapter = new RecyclerViewAdapter(data, MainActivity.this);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getApplicationContext(), "Nothing found, try again!", Toast.LENGTH_SHORT).show();
                    }
                }
                Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No network network connection!!", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_popular:
                item.setChecked(true);
                loadData("popular");
                return true;

            case R.id.item_top_rated:
                item.setChecked(true);
                loadData("top_rated");
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

}

