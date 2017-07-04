package udacity.winni.popsmovie.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by winniseptiani on 6/16/17.
 */

public class MovieVM implements Parcelable {

    private long id;

    private String title;

    private String originalTitle;

    private String poster;

    private String overview;

    private int releaseYear;

    private String releaseDate;

    private long runtime;

    private double voteAverage;

    public MovieVM() {

    }

    protected MovieVM(Parcel in) {
        id = in.readLong();
        title = in.readString();
        originalTitle = in.readString();
        poster = in.readString();
        overview = in.readString();
        releaseYear = in.readInt();
        runtime = in.readLong();
        voteAverage = in.readDouble();
        releaseDate = in.readString();
    }

    public static final Creator<MovieVM> CREATOR = new Creator<MovieVM>() {
        @Override
        public MovieVM createFromParcel(Parcel in) {
            return new MovieVM(in);
        }

        @Override
        public MovieVM[] newArray(int size) {
            return new MovieVM[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPosterPath(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeString(poster);
        dest.writeString(overview);
        dest.writeInt(releaseYear);
        dest.writeLong(runtime);
        dest.writeDouble(voteAverage);
        dest.writeLong(id);
        dest.writeString(releaseDate);
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public long getRuntime() {
        return runtime;
    }

    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
