package br.com.bossini.movieproject;

import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import DetailFragment.BackdropFragment;
import utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    ArrayList<NetworkUtils.Movie> mPopularList;
    ArrayList<NetworkUtils.Movie> mTopTopRatedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new FetchMovies().execute(); //New code

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class FetchMovies extends AsyncTask<Void,Void,Void> {

        public String popularMovies;
        public String topRatedMovies;

        @Override
        protected Void doInBackground(Void... voids) {


            String popularMoviesURL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="+getString(R.string.api_key);
            String topRatedMoviesURL = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key="+getString(R.string.api_key);

            mPopularList = new ArrayList<>();
            mTopTopRatedList = new ArrayList<>();

            try {
                if(NetworkUtils.networkStatus(MainActivity.this)){
                    mPopularList = NetworkUtils.fetchData(popularMoviesURL); //Get popular movies
                    mTopTopRatedList = NetworkUtils.fetchData(topRatedMoviesURL); //Get top rated movies
                }else{
                    Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;

        }

        public class MovieDetailActivity extends AppCompatActivity {

            private final String TAG = MovieDetailActivity.class.getSimpleName();

            public static final String BASE_URL ="https://image.tmdb.org/t/p/w185";

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_movie_detail);
                Intent intent = getIntent();
                Movie mov_intent = (Movie)        intent.getSerializableExtra("detail");
            }

        }
    }



}
