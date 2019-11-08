package com.company;

import com.company.Jsonable;

import javax.json.*;
import java.io.FileOutputStream;
import java.io.FileReader;

public class Movie implements Jsonable {

    String title, director, genre;
    Integer year;

    Movie(){};

    Movie(String title, String director, String genre, Integer year){
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.year = year;
    }

    @Override
    public boolean writeJsonToFile(String filename) {

        JsonObject movie = Json.createObjectBuilder()
                .add("Title", title)
                .add("Year", year)
                .add("Director", director)
                .add("Genre", genre)
                .build();

        try{

            FileOutputStream fo = new FileOutputStream(filename);
            fo.write(movie.toString().getBytes());
            System.out.println("Success");

        } catch (Exception e){
            return false;
        }

        return true;
    }

    @Override
    public Jsonable readFromJsonFile(String filename) {
        try {
            FileReader fr = new FileReader(filename);
            JsonReader jsonReader = Json.createReader(fr);
            JsonObject movieJson = jsonReader.readObject();
            Movie movie = new Movie(
                    movieJson.getString("Title"),
                    movieJson.getString("Director"),
                    movieJson.getString("Genre"),
                    movieJson.getInt("Year")
            );
            return movie;

        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public String toString(){
        return title + " " + year + " " + director + " " + genre;
    }

    public static void main(String[] args){
        Movie movie = new Movie("joker", "no idea", "thriller", 2019);
        movie.writeJsonToFile("joker.json");
        Movie movie2 = new Movie();
        movie2.readFromJsonFile("joker.json");
        System.out.println(movie2.readFromJsonFile("joker.json"));
    }
}
