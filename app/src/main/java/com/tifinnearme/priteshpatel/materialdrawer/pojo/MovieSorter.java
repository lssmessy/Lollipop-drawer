package com.tifinnearme.priteshpatel.materialdrawer.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by pritesh.patel on 13-04-15.
 */
public class MovieSorter {
    public void searchMovieByName(ArrayList<Movie> movies)
    {
        Collections.sort(movies,new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });
    }
    public void searchMovieByDate(ArrayList<Movie> movies)

    {
        Collections.sort(movies,new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.getReleaseDate().compareTo(rhs.getReleaseDate());

            }
        });
    }
    public void searchMovieByRatings(ArrayList<Movie> movies)

    {
        Collections.sort(movies,new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                int lhsRatings= (int) lhs.getVotes();
                int rhsRatings= (int) rhs.getVotes();
                if(lhsRatings<rhsRatings)
                {
                    return 1;
                }
                else if(rhsRatings<lhsRatings)
                {
                    return -1;
                }
                else{
                    return 0;
                }


            }
        });
    }

}
