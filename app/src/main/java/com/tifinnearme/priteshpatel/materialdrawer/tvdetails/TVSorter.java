package com.tifinnearme.priteshpatel.materialdrawer.tvdetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by pritesh.patel on 13-04-15.
 */
public class TVSorter {
    public void searchMovieByName(ArrayList<TV> movies)
    {
        Collections.sort(movies,new Comparator<TV>() {
            @Override
            public int compare(TV lhs, TV rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });
    }
    public void searchMovieByDate(ArrayList<TV> movies)

    {
        Collections.sort(movies,new Comparator<TV>() {
            @Override
            public int compare(TV lhs, TV rhs) {
                return lhs.getReleaseDate().compareTo(rhs.getReleaseDate());

            }
        });
    }
    public void searchMovieByRatings(ArrayList<TV> movies)

    {
        Collections.sort(movies,new Comparator<TV>() {
            @Override
            public int compare(TV lhs, TV rhs) {
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
