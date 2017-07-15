package udacity.winni.popsmovie.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by winniseptiani on 7/8/17.
 */

public class MovieReviewVM implements Parcelable{

    private String id;

    private String author;

    private String content;

    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
