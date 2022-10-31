package com.example.breedsapplication.service;

import java.util.List;
import java.util.Map;

public interface FavoriteService {
    Map<String, List<String>> list();

    void insert(String breed, String image);

    void remove(String image);

    void removeBreed(String breed);

    boolean isFavorite(String image);
}
