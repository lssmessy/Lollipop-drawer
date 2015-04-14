package com.tifinnearme.priteshpatel.materialdrawer.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.tifinnearme.priteshpatel.materialdrawer.logging.L;
import com.tifinnearme.priteshpatel.materialdrawer.material_test.MyApplication;

import java.util.Date;

/**
 * Created by PRITESH on 12-04-2015.
 */
public class Movie implements Parcelable{
    private long id;
    private String title;
    private String urlthumbNail;
    private Date releaseDate;
    private long votes;

    public Movie(){

    }
    public Movie(Parcel input){
        id=input.readLong();
        title=input.readString();
        releaseDate=new Date(input.readLong());
        votes=input.readLong();


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

    @Override
    public int describeContents() {
        L.t(MyApplication.getContext(), "Describes movie contents");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(urlthumbNail);
        dest.writeLong(releaseDate.getTime());//Convert Date object to long
        dest.writeLong(votes);

    }
    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
