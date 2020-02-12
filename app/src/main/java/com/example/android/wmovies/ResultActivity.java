package com.example.android.wmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.wmovies.Utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {

    String SEARCH_MOVIE_URL = "https://yts.lt/api/v2/movie_details.json?movie_id=";
    TextView namev , ratingV , descriptionV ; Button link_720pV , link_1080pV ;TextView yearV ;ImageView image_view ,back_image;
    String link_720p,link_1080p;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        progressDialog = new ProgressDialog(ResultActivity.this);
        progressDialog.setMessage("Loading ...");
        Intent i = getIntent();
        String movie_id = i.getStringExtra("movie_id");
        //TextView name = findViewById(R.id.result_name);
        back_image = findViewById(R.id.back_to_main_2);
        image_view = (ImageView)findViewById(R.id.result_image);
        namev = findViewById(R.id.result_name);
        yearV = findViewById(R.id.result_year);
        ratingV =findViewById(R.id.result_rating);
        descriptionV =findViewById(R.id.result_description);
        link_720pV = findViewById(R.id.result_720_link);
        link_1080pV = findViewById(R.id.result_1080_link);

        SEARCH_MOVIE_URL += movie_id;

        ResultTask resultTask = new ResultTask();
        resultTask.execute(SEARCH_MOVIE_URL);
        progressDialog.show();

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        link_720pV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(link_720p));
                startActivity(i);
            }
        });
        link_1080pV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(link_1080p));
                startActivity(i);
            }
        });
    }

    public class ResultTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            Utils utils = new Utils();
            String json  = utils.fetchMovieList(strings[0]);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try{
                updateUI(result);
                progressDialog.dismiss();
            }
            catch (JSONException e)
            {
                System.out.println(e);
            }
        }
    }

    private void updateUI(String result) throws JSONException {
        JSONObject jsonObj = new JSONObject(result);
        JSONObject data = jsonObj.getJSONObject("data");
        String mov = data.getString("movie");
        JSONObject jsonObject = new JSONObject(mov);
        String name = jsonObject.getString("title");
        String rating = jsonObject.getString("rating");
        String desc = jsonObject.getString("description_full");
        String year = jsonObject.getString("year");
        //String background_img = jsonObject.getString("background_image");
        String image_url = jsonObject.getString("large_cover_image");
        String torrents=jsonObject.getString("torrents");
        JSONArray jsonArray = new JSONArray(torrents);
        String l7 = jsonArray.getString(0);
        JSONObject j1 =new JSONObject(l7);
        String  l8 = jsonArray.getString(1);
        JSONObject j2 = new JSONObject(l8);
        String url_720 = j1.getString("url");
        String url_1080 = j2.getString("url");

        //Picasso.get().load(background_img).into(back_image);
        Picasso.get().load(image_url).into(image_view);
        yearV.setText(""+year);
        namev.setText(name);
        ratingV.setText(""+rating+"");
        descriptionV.setText(desc);
        link_720p = url_720;
        link_1080p = url_1080;
        //link_720pV.setText(url_720);
        //link_1080pV.setText(url_1080);
    }

}
