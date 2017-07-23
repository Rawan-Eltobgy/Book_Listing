package com.example.android.book_listing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private String key;
    private static final String LOG_TAG = list.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button searchBtn = (Button) findViewById(R.id.search_button);
        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                EditText eSearch = (EditText) findViewById(R.id.search_edit_text);
                key = eSearch.getText().toString().trim();
                Log.e(LOG_TAG, " key is trial 1 : "+key );
                Intent i = new Intent(MainActivity.this, list.class);
                i.putExtra("Search", key);
                startActivity(i);
            }
        });
    }

}
