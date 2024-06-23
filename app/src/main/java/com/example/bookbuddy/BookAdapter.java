package com.example.bookbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context context;
    private JSONArray books;

    public BookAdapter(Context context, JSONArray books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
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
            Glide.with(context).load(imageUrl).into(holder.bookCoverImageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return books.length();
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

