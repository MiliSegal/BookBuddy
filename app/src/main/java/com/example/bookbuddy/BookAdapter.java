package com.example.bookbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.squareup.picasso.Picasso;
import java.util.List;

public class BookAdapter extends BaseAdapter {

    private Context context;
    private List<BookModel> books;

    public BookAdapter(Context context, List<BookModel> books) {
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

        BookModel book = books.get(position);

        titleTextView.setText(book.getTitle());
        authorTextView.setText(book.getAuthor());
        descriptionTextView.setText(book.getDescription());

        // Use Picasso to load the drawable image resource
        Picasso.get().load(book.getImageResId()).into(bookCoverImageView);

        return convertView;
    }
}
