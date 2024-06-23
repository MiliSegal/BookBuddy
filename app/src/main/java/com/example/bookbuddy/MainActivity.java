package com.example.bookbuddy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Swipe swipe;
    private ArrayList<BookModel> booksList = new ArrayList<>();
    private ImageView bookCoverImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookCoverImageView = findViewById(R.id.bookCoverImageView);

        new FetchBookTask().execute("https://openlibrary.org/works/OL45804W.json");
    }

    private class FetchBookTask extends AsyncTask<String, Void, BookModel> {

        @Override
        protected BookModel doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            BookModel book = null;

            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a Bitmap
                inputStream = urlConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                // Retrieve other book details from JSON response
                String name = null;
                String author = null;
                String coverImageUrl = null;
                String summary = null;

                String jsonResponse = convertStreamToString(inputStream);
                JSONObject jsonObject = new JSONObject(jsonResponse);

                name = jsonObject.optString("title");
                author = getAuthor(jsonObject);
                coverImageUrl = getCoverImageUrl(jsonObject);
                summary = jsonObject.optString("description");

                book = new BookModel(name, author, coverImageUrl, summary);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return book;
        }

        @Override
        protected void onPostExecute(BookModel book) {
            if (book != null) {
                // Update UI with fetched book data
                TextView bookTitleTextView = findViewById(R.id.bookTitleTextView);
                bookTitleTextView.setText(book.getName());

                TextView bookAuthorTextView = findViewById(R.id.bookAuthorTextView);
                bookAuthorTextView.setText(book.getAuthor());

                TextView bookSummaryTextView = findViewById(R.id.bookSummaryTextView);
                bookSummaryTextView.setText(book.getSummary());

                // Load cover image into ImageView
                new DownloadImageTask(bookCoverImageView).execute(book.getCoverImageUrl());
            }
        }

        private String getAuthor(JSONObject jsonObject) throws JSONException {
            StringBuilder authors = new StringBuilder();
            JSONArray authorsArray = jsonObject.optJSONArray("authors");
            if (authorsArray != null) {
                for (int i = 0; i < authorsArray.length(); i++) {
                    JSONObject authorObj = authorsArray.getJSONObject(i);
                    authors.append(authorObj.optString("name")).append(", ");
                }
                if (authors.length() > 2) {
                    authors.setLength(authors.length() - 2);
                }
            }
            return authors.toString();
        }

        private String getCoverImageUrl(JSONObject jsonObject) {
            String coverImageUrl = null;
            JSONArray coversArray = jsonObject.optJSONArray("covers");
            if (coversArray != null && coversArray.length() > 0) {
                int coverId = coversArray.optInt(0);
                coverImageUrl = "https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg";
            }
            return coverImageUrl;
        }

        private String convertStreamToString(InputStream inputStream) {
            java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
