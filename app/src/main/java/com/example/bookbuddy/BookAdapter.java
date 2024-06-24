package com.example.bookbuddy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BookAdapter extends BaseAdapter {

    private Context context;
    private List<JSONObject> books;

    public BookAdapter(Context context, List<JSONObject> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.bookName);
        TextView authorTextView = convertView.findViewById(R.id.authorName);
        TextView descriptionTextView = convertView.findViewById(R.id.desName);
        ImageView bookCoverImageView = convertView.findViewById(R.id.bookCoverImageView);

        try {
            JSONObject book = books.get(position);
            String title = book.getString("title");
            String author = book.getString("author");
            String description = book.getString("description");

            titleTextView.setText(title);
            authorTextView.setText(author);
            descriptionTextView.setText(description);

            if (book.has("cover_id") && !book.isNull("cover_id")) {
                String coverId = book.getString("cover_id");
                String imageUrl = "http://covers.openlibrary.org/b/id/" + coverId + "-L.jpg";
                Log.d("BookAdapter", "Loading image URL: " + imageUrl);
                Picasso.get().load(imageUrl).into(bookCoverImageView);
            } else {
                bookCoverImageView.setImageResource(R.drawable.donut); // Placeholder image
                Log.d("BookAdapter", "Cover ID not available");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
