package com.example.breedsapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;

import com.example.breedsapplication.R;

public abstract class AppActivity extends AppCompatActivity {
    /** Parse some data from input intent
     * @param intent Object with input activity data
     */
    protected abstract void parseIntent(Intent intent);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent(getIntent());
    }

    /**
     * Setup toolbar for make activity closable
     */
    protected void setupToolbar() {
        setupToolbar(null);
    }

    /**
     * Setup toolbar for make activity closable
     * @param title Title of toolbar
     */
    protected void setupToolbar(@Nullable String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (title != null) {
            toolbar.setTitle(title);
        }

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
