package com.tifinnearme.priteshpatel.materialdrawer.pojo;

import java.util.Date;

/**
 * Created by PRITESH on 12-04-2015.
 */
public class Movie {
    private long id;
    private String title;
    private String urlthumbNail;
    private Date releaseDate;
    private long votes;

    public Movie(){

    }
    public Movie(long id, String title,String urlthumbNail,Date releaseDate,long votes){
        this.id=id;
        this.title=title;
        this.urlthumbNail=urlthumbNail;
        this.releaseDate=releaseDate;
        this.votes=votes;

    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }



    public long getVotes() {
        return votes;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrlthumbNail() {
        return urlthumbNail;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrlthumbNail(String urlthumbNail) {
        this.urlthumbNail = urlthumbNail;
    }
}
