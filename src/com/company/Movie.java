package com.company;

import com.company.Jsonable;
import netscape.javascript.JSObject;

import javax.json.*;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.Array;
import java.util.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Movie implements Jsonable {

    public String title, released;
    public Integer year, runtime;
    public List<String> genres;
    public List<Writer> writers;
    public List<Actor> actors;
    public Director director;
    public String plot;
    public List<String> languages;
    public List<String> countries;
    public String awards;
    public String poster;
    public List<Rating> ratings;

    public static class Rating {
        public String source;
        public String value;
        public Integer votes;

        public Rating(String source, String value, Integer votes) {
            this.source = source;
            this.value = value;
            this.votes = votes;
        }

        public Rating(String source, String value) {
            this.source = source;
            this.value = value;
            this.votes = null;
        }
    }

    public static class Director {
        String name;

        public Director(String name) {
            this.name = name;
        }
    }

    public static class Writer {
        String name;
        String type;

        public Writer(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }

    public static class Actor {
        String name;
        String as;

        public Actor(String name, String as) {
            this.name = name;
            this.as = as;
        }
    }


    public Movie() {
    }

    public Movie(String title, Integer year, String released, Integer runtime, List<String> genres, Director director, List<Writer> writers, List<Actor> actors, String plot, List<String> languages, List<String> countries, String awards, String poster, List<Rating> ratings) {
        this.title = title;
        this.year = year;
        this.released = released;
        this.runtime = runtime;
        this.genres = genres;
        this.director = director;
        this.writers = writers;
        this.actors = actors;
        this.plot = plot;
        this.languages = languages;
        this.countries = countries;
        this.awards = awards;
        this.poster = poster;
        this.ratings = ratings;
    }

    @Override
    public boolean writeJsonToFile(String filename) {
        JsonArrayBuilder genresJson = Json.createArrayBuilder();
        JsonArrayBuilder languagesJson = Json.createArrayBuilder();
        JsonArrayBuilder countriesJson = Json.createArrayBuilder();
        JsonArrayBuilder writersJson = Json.createArrayBuilder();
        JsonArrayBuilder actorsJson = Json.createArrayBuilder();
        JsonArrayBuilder ratingsJson = Json.createArrayBuilder();

        for (String l : languages) {
            languagesJson.add(l);
        }

        for (String c : countries) {
            countriesJson.add(c);
        }

        for (String g : genres) {
            genresJson.add(g);
        }

        for (Writer w : writers) {
            writersJson.add(Json.createObjectBuilder()
                    .add("Name", w.name)
                    .add("Type", w.type));
        }

        for (Actor a : actors) {
            actorsJson.add(Json.createObjectBuilder()
                    .add("Name", a.name)
                    .add("As", a.as));
        }

        for (Rating r : ratings) {
            try {
                ratingsJson.add(Json.createObjectBuilder()
                        .add("Source", r.source)
                        .add("Value", r.value)
                        .add("Votes", r.votes));
            } catch (NullPointerException e) {
                ratingsJson.add(Json.createObjectBuilder()
                        .add("Source", r.source)
                        .add("Value", r.value));
            }
        }

        JsonObject movie = Json.createObjectBuilder()
                .add("Title", title)
                .add("Year", year)
                .add("Released", released)
                .add("Runtime", runtime)
                .add("Genres", genresJson)
                .add("Director", Json.createObjectBuilder()
                        .add("Name", director.name))
                .add("Writers", writersJson)
                .add("Actors", actorsJson)
                .add("Plot", plot)
                .add("Languages", languagesJson)
                .add("Countries", countriesJson)
                .add("Awards", awards)
                .add("Poster", poster)
                .add("Ratings", ratingsJson)
                .build();
        //System.out.println(movie.toString());
        try {
            FileOutputStream fo = new FileOutputStream(filename);
            fo.write(movie.toString().getBytes());
            System.out.println("Success");
        } catch (Exception e) {
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

            JsonArray genres_json = movieJson.getJsonArray("Genres");
            genres = new ArrayList<>();
            for (int i = 0; i < genres_json.size(); i++) {
                genres.add(genres_json.getString(i));
            }

            JsonArray actors_json = movieJson.getJsonArray("Actors");
            actors = new ArrayList<>();
            for (int i = 0; i < actors_json.size(); i++) {
                Actor actor = new Actor(actors_json.getJsonObject(i).getString("Name"), actors_json.getJsonObject(i).getString("As"));
                actors.add(actor);
            }

            JsonArray lang_json = movieJson.getJsonArray("Languages");
            languages = new ArrayList<>();
            for (int i = 0; i < lang_json.size(); i++) {
                languages.add(lang_json.getString(i));
            }

            JsonArray count_json = movieJson.getJsonArray("Countries");
            countries = new ArrayList<>();
            for (int i = 0; i < count_json.size(); i++) {
                countries.add(count_json.getString(i));
            }

            JsonArray ratings_json = movieJson.getJsonArray("Ratings");
            ratings = new ArrayList<>();
            for (int i = 0; i < ratings_json.size(); i++) {
                try {
                    Rating rating = new Rating(ratings_json.getJsonObject(i).getString("Source"), ratings_json.getJsonObject(i).getString("Value"), ratings_json.getJsonObject(i).getInt("Votes"));
                    ratings.add(rating);
                } catch (NullPointerException e) {
                    Rating rating = new Rating(ratings_json.getJsonObject(i).getString("Source"), ratings_json.getJsonObject(i).getString("Value"));
                    ratings.add(rating);
                }
            }

            JsonArray writers_json = movieJson.getJsonArray("Writers");
            writers = new ArrayList<>();
            for (int i = 0; i < writers_json.size(); i++) {
                Writer writer = new Writer(writers_json.getJsonObject(i).getString("Name"), writers_json.getJsonObject(i).getString("Type"));
                writers.add(writer);
            }

            director = new Director(movieJson.getJsonObject("Director").getString("Name"));
            title = movieJson.getString("Title");
            year = movieJson.getInt("Year");
            released = movieJson.getString("Released");
            runtime = movieJson.getInt("Runtime");
            plot = movieJson.getString("Plot");
            awards = movieJson.getString("Awards");
            poster = movieJson.getString("Poster");

            Movie movie = new Movie(
                    title,
                    year,
                    released,
                    runtime,
                    genres,
                    director,
                    writers,
                    actors,
                    plot,
                    languages,
                    countries,
                    awards,
                    poster,
                    ratings
            );
            return movie;
        } catch (Exception err) {
            System.out.println(err);
            return null;
        }
    }

    public static <T> void main(String[] args) {
        List<Movie> movies = new ArrayList<Movie>();
        Movie m1 = new Movie();
        m1.readFromJsonFile("BladeRunner.json");
        m1.runtime = 90;
        for(int i = 0; i < m1.writers.size(); i++) {
            m1.writers.remove(i);
        }

        Movie m2 = new Movie();
        m2.readFromJsonFile("BladeRunner.json");
        m2.year = 2002;
        m2.runtime = 130;

        Movie m3 = new Movie();
        m3.readFromJsonFile("BladeRunner.json");
        m3.year = 2003;

        movies.add(m1);
        movies.add(m2);
        movies.add(m3);

        //Filter out movies older than 19
        List<Movie> olderThan19 = movies.stream().filter(movie -> movie.year > 2000).collect(Collectors.toList());
        olderThan19.forEach(movie -> System.out.println(movie.year));

        //Filter out movies with runtime under 2 hours
        List<Movie> longerThan2H = movies.stream().filter(movie -> movie.runtime > 120).collect(Collectors.toList());
        longerThan2H.forEach(movie -> System.out.println(movie.runtime));

        //Filter out movies with more than one writer
        List<Movie> singleWriterMovies = movies.stream().filter(movie -> movie.writers.size() < 2).collect(Collectors.toList());
        singleWriterMovies.forEach(movie -> System.out.println(movie.writers));
    }
}

