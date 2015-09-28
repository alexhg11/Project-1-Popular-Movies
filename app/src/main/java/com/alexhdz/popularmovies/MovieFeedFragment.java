package com.alexhdz.popularmovies;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.alexhdz.popularmovies.adapter.ImageAdapter;
import com.alexhdz.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieFeedFragment extends Fragment {

    private ImageAdapter mMovieFeedAdapter;

    public MovieFeedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_moviefeed, container, false);

        GridView gridView =  (GridView) rootView.findViewById(R.id.gridview_moviefeed);
        mMovieFeedAdapter = new ImageAdapter(
                getActivity(),
                new ArrayList<Movie>()
        );
        gridView.setAdapter(mMovieFeedAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieFeed();
    }

    private void updateMovieFeed() {
        FetchMovieFeedTask movieFeedTask = new FetchMovieFeedTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_popularity));
        movieFeedTask.execute(sortOrder);
    }

    public class FetchMovieFeedTask extends AsyncTask<String, Void, List<Movie>> {

        private final String LOG_TAG = FetchMovieFeedTask.class.getSimpleName();

        @Override
        protected List<Movie> doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieFeedJsonStr = null;

            // URI parameters
            String apiKey = "xxxxxxxxxxxxxxxxxx";

            try {
                // Construct the URL for the Movie DB query
                // Possible parameters are avaiable at The Movie DB's API page, at
                // https://www.themoviedb.org/documentation/api
                final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
                final String SORT_PARAM = "sort_by";
                final String APIKEY_PARAM = "api_key";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_PARAM, params[0] + ".desc")
                        .appendQueryParameter(APIKEY_PARAM, apiKey)
                        .build();

                URL url = new URL(builtUri.toString());

                // Create the request to TheMovieDB, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieFeedJsonStr = buffer.toString();

                Log.v(LOG_TAG, movieFeedJsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(movieFeedJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> result) {
            if(result != null && result.size() > 0) {
                mMovieFeedAdapter.setMovies(result);
                // New data is back from the server.  Hooray!
                mMovieFeedAdapter.notifyDataSetChanged();
            }
        }

        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private List<Movie> getMovieDataFromJson(String movieFeedJsonStr) throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String TMD_RESULTS = "results";
            final String TMD_ADULT = "adult";
            final String TMD_BACKDROP = "backdrop_path";
            final String TMD_ID = "id";
            final String TMD_ORIGINAL_LANGUAGE = "original_language";
            final String TMD_ORIGINAL_TITLE = "original_title";
            final String TMD_OVERVIEW = "overview";
            final String TMD_RELEASE_DATE = "release_date";
            final String TMD_POSTER = "poster_path";
            final String TMD_POPULARITY = "popularity";
            final String TMD_TITLE = "title";
            final String TMD_VIDEO = "video";
            final String TMD_VOTE_AVERAGE = "vote_average";
            final String TMD_VOTE_COUNT = "vote_count";

            JSONObject movieFeedJson = new JSONObject(movieFeedJsonStr);
            JSONArray movieArray = movieFeedJson.getJSONArray(TMD_RESULTS);

            ArrayList<Movie> movies = new ArrayList<>();

            // TDM returns a movie list based on sort by parameter sent
            // which for this implementation is popularity.
            for(int i = 0; i < movieArray.length(); i++) {
                // Get the JSON object representing the day
                JSONObject movie = movieArray.getJSONObject(i);

                Movie temp = new Movie();
                temp.setIsAdult(movie.getBoolean(TMD_ADULT));
                temp.setBackdropUri(movie.getString(TMD_BACKDROP));
                temp.setId(movie.getInt(TMD_ID));
                temp.setOriginalLanguage(movie.getString(TMD_ORIGINAL_LANGUAGE));
                temp.setOriginalTitle(movie.getString(TMD_ORIGINAL_TITLE));
                temp.setOverview(movie.getString(TMD_OVERVIEW));
                temp.setReleaseDate(movie.getString(TMD_RELEASE_DATE));
                temp.setPosterUri(movie.getString(TMD_POSTER));
                temp.setPopularity(movie.getDouble(TMD_POPULARITY));
                temp.setTitle(movie.getString(TMD_TITLE));
                temp.setHasVideo(movie.getBoolean(TMD_VIDEO));
                temp.setVoteAverage(movie.getDouble(TMD_VOTE_AVERAGE));
                temp.setVoteCount(movie.getInt(TMD_VOTE_COUNT));

                movies.add(temp);
            }

            return movies;

        }
    }
}
