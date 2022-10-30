package com.example.breedsapplication.adapter.listener;

import android.view.View;

import java.util.List;

public interface OnImageSelected {
    void onImageSelected(int pos, String item, List<String> images, View root);
}
