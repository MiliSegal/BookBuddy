package com.example.bookbuddy;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SwipeFlingAdapterView swipeFlingAdapterView;
    private BookAdapter bookAdapter;
    private List<JSONObject> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        books = new ArrayList<>();
        swipeFlingAdapterView = findViewById(R.id.frame);
        bookAdapter = new BookAdapter(this, books);

        swipeFlingAdapterView.setAdapter(bookAdapter);
        swipeFlingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                if (!books.isEmpty()) {
                    books.remove(0);
                    bookAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
            }

            @Override
            public void onRightCardExit(Object dataObject) {
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });

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
                            for (int i = 0; i < works.length(); i++) {
                                JSONObject book = works.getJSONObject(i);

                                String title = book.getString("title");
                                JSONArray authorsArray = book.getJSONArray("authors");
                                String author = authorsArray.getJSONObject(0).getString("name");
                                String description = book.has("description") ? book.getString("description") : "No description available";
                                String coverId = book.has("cover_id") ? book.getString("cover_id") : null;

                                // Only add books with a valid cover_id
                                if (coverId != null && !coverId.isEmpty()) {
                                    JSONObject bookData = new JSONObject();
                                    bookData.put("title", title);
                                    bookData.put("author", author);
                                    bookData.put("description", description);
                                    bookData.put("cover_id", coverId);

                                    books.add(bookData);
                                }
                            }

                            bookAdapter.notifyDataSetChanged();
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
