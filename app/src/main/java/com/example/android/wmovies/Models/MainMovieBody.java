package com.example.android.wmovies.Models;

public class MainMovieBody {
    String movie_id;
    String name ;
    String rating ;
    String image_url ;
    public MainMovieBody(String name ,String rating,String image_url ,String movie_id){
        this.image_url=image_url;
        this.name=name;
        this.rating=rating;
        this.movie_id=movie_id;
    }

    public String getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getMovie_id() {
        return movie_id;
    }
}
