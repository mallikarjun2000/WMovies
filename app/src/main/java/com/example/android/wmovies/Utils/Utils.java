package com.example.android.wmovies.Utils;

import android.net.Uri;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.example.android.wmovies.MainActivity;
import com.example.android.wmovies.Models.MainMovieBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Utils {
    public Utils()
    {
    }

    public String fetchMovieList(String url){
        String jsonString = "";
        try {
            URL url1 = createURL(url);
            jsonString = makeHttpRequest(url1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public URL createURL(String string_url) throws MalformedURLException {
        URL url = new URL(string_url);
        return url;
    }
    public String makeHttpRequest(URL url)
    {
        String jsonString = null;
        InputStream inputStream;
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            //httpURLConnection.setConnectTimeout(10000);
            inputStream = httpURLConnection.getInputStream();
            jsonString = getJsonFromStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
    public String getJsonFromStream(InputStream inputStream)
    {
        String data = new String();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String  line = "";
        data = "";
        while(line!=null)
        {
            try {
                line = bufferedReader.readLine();
                data = data + line;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
    public static ArrayList<MainMovieBody> fetchArrayListOfMovie(String result) throws JSONException {
        ArrayList<MainMovieBody> arrayList = new ArrayList<MainMovieBody>();
        JSONObject jsonObject = new JSONObject(result);
        String status = jsonObject.getString("status");
        if(status.equals("ok"))
        {
            String data = jsonObject.getString("data");
            JSONObject data_Obj = new JSONObject(data);
            String movies  = data_Obj.getString("movies");
            JSONArray movies_array = new JSONArray(movies);
            if(movies_array.length() == 0)
                return null;
            for(int i=0;i<movies_array.length();i++)
            {
               JSONObject single_movie = movies_array.getJSONObject(i);
               String id = single_movie.getString("id");
               String name = single_movie.getString("title");
               String image_url = single_movie.getString("medium_cover_image");
               String rating = single_movie.getString("rating");
               MainMovieBody movie_obj = new MainMovieBody(name,rating,image_url,id);
               arrayList.add(movie_obj);
            }
        }
        else
            Log.i("FETCH_ARRAY","NOT able to fetch details");
        return arrayList;
    }
}
