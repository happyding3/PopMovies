package com.example.android.popmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by happyding3 on 2016/8/22.
 */
public class Movie implements Parcelable {
     String title;
     String posterPath;
     String overView;
     String voteAverage;
     String releaseDate;

    @Override
    public int describeContents() {
        return 0;
    }
public static final Parcelable.Creator<Movie> CREATOR=new Parcelable.Creator<Movie>(){
    @Override
    public Movie createFromParcel(Parcel parcel) {
        return new Movie(parcel);
    }

    @Override
    public Movie[] newArray(int i) {
        return new Movie[i];
    }
};
    //Storing the Movie data to Parcel object

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(title);
        parcel.writeString(posterPath);
        parcel.writeString(overView);
        parcel.writeString(voteAverage);
        parcel.writeString(releaseDate);

    }


    public Movie(String title, String posterPath, String overView, String voteAverage, String releaseDate){
        this.title=title;
        this.posterPath=posterPath;
        this.overView=overView;
        this.voteAverage=voteAverage;
        this.releaseDate=releaseDate;
    }
    /**
     * retrieving Movie data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     */
    private Movie(Parcel in){
        this.title=in.readString();
        this.posterPath=in.readString();
        this.overView=in.readString();
        this.voteAverage=in.readString();
        this.releaseDate=in.readString();
    }

    /**
     * get title,posterPath...
     */
    public String getTitle(){return title;}
    public String getPosterPath(){return posterPath;}
    public String getOverView(){return overView;}
    public String getVoteAverage(){return voteAverage;}
    public String getReleaseDate(){return releaseDate;}
    public String getAll(){return title
            +"\n"+posterPath
            +"\n"+overView
            +"\n"+voteAverage
            +"\n"+releaseDate;}
}
