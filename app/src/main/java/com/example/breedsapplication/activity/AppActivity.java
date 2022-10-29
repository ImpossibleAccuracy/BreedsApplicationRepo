package com.example.breedsapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;

public abstract class AppActivity extends AppCompatActivity {
    /** Parse some data from input intent
     * @param intent Object with input activity data
     */
    protected abstract void parseIntent(Intent intent);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(
                getWindow(), false);
        parseIntent(getIntent());
    }

    /**
     * Setup toolbar for make activity closable
     * @param toolbar Toolbar view
     */
    protected void setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Close activity on toolbar action
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
