package com.example.breedsapplication;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

public class BreedApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
