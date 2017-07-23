package com.example.android.book_listing;

/**
 * Created by user on 7/18/2017.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class BookLoader extends AsyncTaskLoader<List<Book>> {
    String baseRequestUrl;
    String jsonResponse = "";
    String key;
    ProgressBar mProgressBar;

    public BookLoader(Context context, String baseRequestUrl, String key, ProgressBar mProgressBar) {
        super(context);
        this.baseRequestUrl = baseRequestUrl;
        this.key = key;
        this.mProgressBar = mProgressBar;
    }

      /**
     * BASE_REQUEST_URL += key;
     * Log.e(LOG_TAG, " Request url : "+BASE_REQUEST_URL );
     **/


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Book> extractFeatureFromJson(String bookJSON) {
        // If the JSON string is empty or null, then return early.
        String LOG_TAG = BookLoader.class.getName();
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding books to
        List<Book> list = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            Log.e(LOG_TAG, "Fetching properties .....  ");
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(bookJSON);
            // Extract the JSONArray associated with the key called "items",
            JSONArray itemsArray = baseJsonResponse.optJSONArray("items");
            if (itemsArray != null) {
                for (int i = 0; i < itemsArray.length(); i++) {

                    JSONObject currentBook = itemsArray.getJSONObject(i);
                    JSONObject volumeObject = currentBook.optJSONObject("volumeInfo");
                    String title = volumeObject.optString("title");
                    String publisher = volumeObject.optString("publisher");
                    String publishedDate = volumeObject.optString("publishedDate");
                    String description = volumeObject.optString("description");
                    JSONArray authorArray = volumeObject.optJSONArray("authors");
                    String author = "";
                    if (authorArray != null) {
                        for (int j = 0; j < authorArray.length(); j++) {
                            author = authorArray.optString(0);
                        }
                    }
                    list.add(new Book("Title: " + title, "Author: " + author, "Publisher: " + publisher, "Published Date: " + publishedDate, "Description: " + description));
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "NO PROPERTIES FOUND  ", e);
        }

        return list;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        //user url here
        /** Tag for log messages */
        String LOG_TAG = BookLoader.class.getName();
        final String APP_ID = "booklisting-174212";
        Log.e(LOG_TAG, "REQUEST is: trial 2 : " + baseRequestUrl);
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        baseRequestUrl += key;
        Uri uri = Uri.parse(baseRequestUrl);
        // .appendQueryParameter("ke.buildUpon()y", APP_ID).build();

        URL url = null;
        try {
            url = new URL(baseRequestUrl);
            Log.e(LOG_TAG, "REQUEST After editing : " + url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {


                inputStream = urlConnection.getInputStream();

                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the books JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        List<Book> books = extractFeatureFromJson(jsonResponse);
        return books;
    }
}



