package com.example.android.wmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.wmovies.Adapters.MainMoviesAdapter;
import com.example.android.wmovies.Models.MainMovieBody;
import com.example.android.wmovies.Utils.Utils;

import org.json.JSONException;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    String MOVIE_QUERY_URL = "https://yts.lt/api/v2/list_movies.json?query_term=";
    GridView gridView;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent in = getIntent();
        gridView = findViewById(R.id.search_results_list);
        back = findViewById(R.id.back_to_main);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String query = in.getStringExtra("query");
        MOVIE_QUERY_URL+=query;
        SearchTask searchTask = new SearchTask();
        searchTask.execute(MOVIE_QUERY_URL);
        Log.i("TAGG",MOVIE_QUERY_URL);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainMovieBody mainMovieBody = (MainMovieBody) gridView.getItemAtPosition(position);
                Intent in = new Intent(SearchResultActivity.this,ResultActivity.class);
                Toast.makeText(SearchResultActivity.this,"Opening ",Toast.LENGTH_SHORT).show();
                in.putExtra("movie_id", ""+mainMovieBody.getMovie_id());
                startActivity(in);
            }
        });

    }
    public class SearchTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String json="";
            if (strings.length > 0) {
                Utils utils = new Utils();
                json = utils.fetchMovieList(strings[0]);
            }
            Log.i("TAGG",json);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                ArrayList<MainMovieBody> arrayList = Utils.fetchArrayListOfMovie(result);
                MainMoviesAdapter adapter = new MainMoviesAdapter(SearchResultActivity.this,arrayList);
                gridView.setAdapter(adapter);
            }
            catch (JSONException e)
            {
                Toast.makeText(SearchResultActivity.this,"JSOn exeception ",Toast.LENGTH_SHORT).show();
            }

        }
    }
}
