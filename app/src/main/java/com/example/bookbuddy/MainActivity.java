// MainActivity.java
package com.example.bookbuddy;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView titleTextView, authorTextView, descriptionTextView;
    private ImageView bookCoverImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleTextView = findViewById(R.id.bookName);
        authorTextView = findViewById(R.id.authorName);
        descriptionTextView = findViewById(R.id.desName);
        bookCoverImageView = findViewById(R.id.bookCoverImageView);

        fetchBookData();
    }

    private void fetchBookData() {
        String url = "https://openlibrary.org/subjects/love.json";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray works = response.getJSONArray("works");
                            if (works.length() > 0) {
                                JSONObject firstBook = works.getJSONObject(0);

                                String title = firstBook.getString("title");
                                JSONArray authorsArray = firstBook.getJSONArray("authors");
                                String author = authorsArray.getJSONObject(0).getString("name");
                                String description = firstBook.has("description") ? firstBook.getString("description") : "No description available";
                                String coverId = firstBook.getString("cover_id");
                                String imageUrl = "http://covers.openlibrary.org/b/id/" + coverId + "-L.jpg";

                                titleTextView.setText(title);
                                authorTextView.setText(author);
                                descriptionTextView.setText(description);
                                Picasso.get().load(imageUrl).into(bookCoverImageView);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
}
