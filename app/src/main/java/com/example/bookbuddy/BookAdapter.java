package com.example.bookbuddy;// BookAdapter.java

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookbuddy.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private JSONArray books;
    private Context context;
    private List<Integer> swipedPositions;

    public BookAdapter(Context context, JSONArray books) {
        this.context = context;
        this.books = books;
        this.swipedPositions = new ArrayList<>();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        if (swipedPositions.contains(position)) {
            // Skip binding swiped books
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            return;
        }

        try {
            JSONObject book = books.getJSONObject(position);
            String title = book.getString("title");
            JSONArray authorsArray = book.getJSONArray("authors");
            String author = authorsArray.getJSONObject(0).getString("name");
            String description = book.has("description") ? book.getString("description") : "No description available";
            String coverId = book.getString("cover_id");
            String imageUrl = "http://covers.openlibrary.org/b/id/" + coverId + "-L.jpg";

            holder.titleTextView.setText(title);
            holder.authorTextView.setText(author);
            holder.descriptionTextView.setText(description);
            Picasso.get().load(imageUrl).into(holder.bookCoverImageView);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return books.length();
    }

    public void deleteItem(int position) {
        books.remove(position);
        swipedPositions.add(position); // Add swiped position to the list
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, books.length());
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, authorTextView, descriptionTextView;
        ImageView bookCoverImageView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.bookName);
            authorTextView = itemView.findViewById(R.id.authorName);
            descriptionTextView = itemView.findViewById(R.id.desName);
            bookCoverImageView = itemView.findViewById(R.id.bookCoverImageView);
        }
    }
}
