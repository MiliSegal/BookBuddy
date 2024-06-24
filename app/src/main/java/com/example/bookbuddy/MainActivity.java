package com.example.bookbuddy;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SwipeFlingAdapterView swipeFlingAdapterView;
    private BookAdapter bookAdapter;
    private List<BookModel> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeFlingAdapterView = findViewById(R.id.frame);

        // Initialize book list
        books = new ArrayList<>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            books.add(new BookModel(
                    MyData.nameArray[i],
                    MyData.authorArray[i],
                    MyData.summeryArray[i],
                    MyData.drawableArray[i]
            ));
        }

        bookAdapter = new BookAdapter(this, books);
        swipeFlingAdapterView.setAdapter(bookAdapter);

        swipeFlingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                books.remove(0);
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {}
            @Override
            public void onRightCardExit(Object dataObject) {}
            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {}
            @Override
            public void onScroll(float scrollProgressPercent) {}
        });
    }
}
