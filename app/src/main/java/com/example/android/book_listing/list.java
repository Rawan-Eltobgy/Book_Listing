package com.example.android.book_listing;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class list extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    private static final String LOG_TAG = list.class.getName();
    private static final int BOOK_LOADER_ID = 1;
    private static String BASE_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";
    final String APP_ID = "booklisting-174212";
    ProgressBar mProgressBar;
    /**
     * TextView that is displayed when the list is empty
     */
    private ListView bookList;
    private String key;
    private TextView mEmptyStateTextView;
    private BookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // mProgressBar.setVisibility(View.VISIBLE);
        bookList = (ListView) findViewById(R.id.book_list);

        key = getIntent().getExtras().getString("Search");
        // Log.e(LOG_TAG, " key is: trial 2 : "+key );
        //Log.e(LOG_TAG, " Request url : "+BASE_REQUEST_URL );
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookList.setEmptyView(mEmptyStateTextView);
        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookList.setAdapter(mAdapter);
        // Get a reference to the LoaderManager, in order to interact with loaders.
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        Log.e(LOG_TAG, " Loader created " + BASE_REQUEST_URL);

        return new BookLoader(this, BASE_REQUEST_URL, key, mProgressBar);

    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.loading);
        mProgressBar.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_Books);
        mAdapter.clear();
        // If there is a valid list , then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        } else {

            mAdapter.clear();
            key = "";
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();

    }

}

