package com.example.android.wmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.wmovies.Adapters.MainMoviesAdapter;
import com.example.android.wmovies.Models.MainMovieBody;
import com.example.android.wmovies.Utils.Utils;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String LATEST_MOVIES_URL ="https://******/api/v2/list_movies.json?";
    String BASE_URL = LATEST_MOVIES_URL;
    String sort_by="date_added";
    String Order_by="desc";
    String limit="50";

    GridView movieistView ;
    EditText editText;
    ImageView imageView;
    Button b1,b2,b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.Rating);
        b2=  findViewById(R.id.download);
        b3 = findViewById(R.id.Latest);
        editText = findViewById(R.id.main_search_text);
        imageView = findViewById(R.id.main_search_image);
        movieistView = findViewById(R.id.main_grid_view);
        final MovieDetails movieDetails = new MovieDetails();
        LATEST_MOVIES_URL +="sort_by="+sort_by +"&"+"order_by="+Order_by +"&"+"limit="+limit;
        Log.i("TAGI",""+LATEST_MOVIES_URL);
        movieDetails.execute(LATEST_MOVIES_URL);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editText.getText().toString().trim();
                Intent i = new Intent(MainActivity.this,SearchResultActivity.class);
                i.putExtra("query",query);
                startActivity(i);
            }
        });

        movieistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainMovieBody mainMovieBody = (MainMovieBody) movieistView.getItemAtPosition(position);
                Intent in = new Intent(MainActivity.this,ResultActivity.class);
                in.putExtra("movie_id", ""+mainMovieBody.getMovie_id());
                startActivity(in);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetails movieDetails1 = new MovieDetails();
                sort_by="rating";
                BASE_URL ="https://yts.lt/api/v2/list_movies.json?sort_by="+sort_by +"&"+"order_by="+Order_by +"&"+"limit="+limit;
                //LATEST_MOVIES_URL +="sort_by="+sort_by +"&"+"order_by="+Order_by +"&"+"limit="+limit;
                Log.i("TAGI",""+BASE_URL);
                movieDetails1.execute(BASE_URL);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetails movieDetails2 = new MovieDetails();
                sort_by="download_count";
                BASE_URL ="https://yts.lt/api/v2/list_movies.json?sort_by="+sort_by +"&"+"order_by="+Order_by +"&"+"limit="+limit;
                //LATEST_MOVIES_URL +="sort_by="+sort_by +"&"+"order_by="+Order_by +"&"+"limit="+limit;
                Log.i("TAGI",""+BASE_URL);
                movieDetails2.execute(BASE_URL);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetails movieDetails3 = new MovieDetails();
                sort_by="date_added";
                BASE_URL ="https://yts.lt/api/v2/list_movies.json?sort_by="+sort_by +"&"+"order_by="+Order_by +"&"+"limit="+limit;
                Log.i("TAGI",""+BASE_URL);
                movieDetails3.execute(BASE_URL);
            }
        });
    }

    public class MovieDetails extends AsyncTask<String,Void ,String >{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String json="";
            if (strings.length > 0) {
                Utils utils = new Utils();
                json = utils.fetchMovieList(strings[0]);
            }
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                ArrayList<MainMovieBody> arrayList = Utils.fetchArrayListOfMovie(result);
                if(arrayList == null)
                    Toast.makeText(MainActivity.this,"No Results Found (;_;)",Toast.LENGTH_SHORT).show();
                else {
                    MainMoviesAdapter adapter = new MainMoviesAdapter(MainActivity.this, arrayList);
                    movieistView.setAdapter(adapter);
                }
            }
            catch (JSONException e)
            {
                Toast.makeText(MainActivity.this,"NO Results",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
