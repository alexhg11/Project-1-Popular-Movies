package com.alexhdz.popularmovies.model;

public class Movie {

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
