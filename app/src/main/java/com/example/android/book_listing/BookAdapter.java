package com.example.android.book_listing;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 7/17/2017.
 */
public class BookAdapter extends ArrayAdapter<Book> {


    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Book currentBook = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        titleTextView.setText(currentBook.getTitle());

        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        authorTextView.setText(currentBook.getAuthor());

        TextView publisherTextView = (TextView) listItemView.findViewById(R.id.publisher);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        publisherTextView.setText(currentBook.getPublisher());

        TextView publishDateTextView = (TextView) listItemView.findViewById(R.id.publishedDate);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        publishDateTextView.setText(currentBook.getPublishedDate());

        TextView DescriptionTextView = (TextView) listItemView.findViewById(R.id.description);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        DescriptionTextView.setText(currentBook.getDescription());
        return listItemView;
    }
}


