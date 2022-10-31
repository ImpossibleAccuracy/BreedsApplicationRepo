package com.example.breedsapplication.fragment.favorite;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.breedsapplication.service.FavoriteService;
import com.example.breedsapplication.service.FavoriteServiceDBImpl;

import java.util.List;
import java.util.Map;

public class FavoritesViewModel extends AndroidViewModel {
    private final MutableLiveData<Map<String, List<String>>> favorites;
    private final FavoriteService service;

    public FavoritesViewModel(Application application) {
        super(application);
        favorites = new MutableLiveData<>();
        service = new FavoriteServiceDBImpl(application);
    }

    public LiveData<Map<String, List<String>>> getFavorites() {
        return favorites;
    }

    public void loadFavorites() {
        favorites.postValue(service.list());
    }
}