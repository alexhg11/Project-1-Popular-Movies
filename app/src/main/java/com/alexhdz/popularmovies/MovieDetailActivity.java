package com.alexhdz.popularmovies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexhdz.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_moviedetail);

        // Get object from intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {

            Movie movie = intent.getExtras().getParcelable("com.alexhdz.popularmovies.model.Movie");

            FetchPosterImageTask posterTask = new FetchPosterImageTask(
                    (ImageView) findViewById(R.id.imgPoster)
            );

            try {
                posterTask.execute(movie.getPosterUri());
            } catch ( NullPointerException e ) {
                StringBuilder url = new StringBuilder();
                url.append("android.resource://com.alexhdz.popularmovies/");
                url.append(R.drawable.not_available);
                posterTask.execute(url.toString());
            }

            ((TextView) findViewById(R.id.txtTitle))
                    .setText(movie.getTitle());

            ((TextView) findViewById(R.id.txtReleaseDate))
                    .setText(movie.getReleaseDate());

            ((TextView) findViewById(R.id.txtVoteAverage))
                    .setText(Double.toString(movie.getVoteAverage()));

            ((TextView) findViewById(R.id.txtOverview))
                    .setText(movie.getOverview());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_moviedetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class FetchPosterImageTask extends AsyncTask<String,Void,Bitmap> {

        private final String LOG_TAG = FetchPosterImageTask.class.getSimpleName();

        private ImageView imgPoster;
        private String posterUrl;

        /**
         * Constructor with reference to ImageView
         * @param imgPoster ImageView placeholder
         */
        public FetchPosterImageTask(ImageView imgPoster){
            this.imgPoster = imgPoster;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // Construct URL
            final String base = "http://image.tmdb.org/t/p/w342/";
            posterUrl = base + params[0];

            // Download image
            Bitmap image = null;
            try{
                InputStream in = new URL(posterUrl).openStream();
                image = BitmapFactory.decodeStream(in);
            }catch(Exception e){
                Log.e(LOG_TAG, "Error ", e);
                return null;
            }

            return image;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null) {
                imgPoster.setImageBitmap(bitmap);
            }else{
                imgPoster.setLayoutParams(new LinearLayout.LayoutParams(342, 513));
                imgPoster.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imgPoster.setPadding(8, 8, 8, 8);
                Picasso.with(getBaseContext()).load(posterUrl.toString()).into(imgPoster);
            }
        }
    }
}
