package com.alhusseiny.till_man;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
    }

    public void receive(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void sent(View view) {
        startActivity(new Intent(this, SentActivity.class));
    }
}
