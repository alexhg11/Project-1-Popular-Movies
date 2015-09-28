package com.alexhdz.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    // Properties
    private boolean isAdult;
    private String backdropUri;
    private int id;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private String releaseDate;
    private String posterUri;
    private double popularity;
    private String title;
    private boolean hasVideo;
    private double voteAverage;
    private int voteCount;

    // Empty Constructor method
    public Movie () {
        // do nothing.
    }

    // Getters & Setters
    public boolean isAdult() {
        return isAdult;
    }

    public void setIsAdult(boolean isAdult) {
        this.isAdult = isAdult;
    }

    public String getBackdropUri() {
        return backdropUri;
    }

    public void setBackdropUri(String backdropUri) {
        this.backdropUri = backdropUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterUri() {
        return posterUri;
    }

    public void setPosterUri(String posterUri) {
        this.posterUri = posterUri;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    // Interface Methods

    /**
     * Parcelable constructor
     *
     * @param source Parcel source
     */
    protected Movie(Parcel source){
        this.isAdult = source.readByte() != 0;
        this.backdropUri = source.readString();
        this.id = source.readInt();
        this.originalLanguage = source.readString();
        this.originalTitle = source.readString();
        this.overview = source.readString();
        this.releaseDate = source.readString();
        this.posterUri = source.readString();
        this.popularity = source.readDouble();
        this.title = source.readString();
        this.hasVideo = source.readByte() != 0;
        this.voteAverage = source.readDouble();
        this.voteCount = source.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isAdult ? 1 : 0));
        dest.writeString(backdropUri);
        dest.writeInt(id);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(posterUri);
        dest.writeDouble(popularity);
        dest.writeString(title);
        dest.writeByte((byte) (hasVideo ? 1 : 0));
        dest.writeDouble(voteAverage);
        dest.writeInt(voteCount);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "adult='" + isAdult + '\'' +
                ", backdrop='" + backdropUri + '\'' +
                ", id='" + id + '\'' +
                ", original_language='" + originalLanguage + '\'' +
                ", original_title='" + originalTitle + '\'' +
                ", overview='" + overview + '\'' +
                ", release_date='" + releaseDate + '\'' +
                ", poster='" + posterUri + '\'' +
                ", popularity='" + popularity + '\'' +
                ", title='" + title + '\'' +
                ", video='" + hasVideo + '\'' +
                ", vote_average='" + voteAverage + '\'' +
                ", vote_count='" + voteCount + '\'' +
                '}';
    }
}
