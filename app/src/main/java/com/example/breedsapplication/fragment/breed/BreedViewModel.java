package com.example.breedsapplication.fragment.breed;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.breedsapplication.model.Breed;
import com.example.breedsapplication.payload.BreedResponse;
import com.example.breedsapplication.retrofit.APIClient;
import com.example.breedsapplication.retrofit.BreedsService;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Response;
import retrofit2.Retrofit;

public class BreedViewModel extends ViewModel {
    private final MutableLiveData<List<Breed>> breedsLiveData;

    public BreedViewModel() {
        breedsLiveData = new MutableLiveData<>(null);
    }

    public MutableLiveData<List<Breed>> getBreeds() {
        return breedsLiveData;
    }

    /**
     * Calls the API and gets the breeds list
     */
    public void loadBreeds() {
        Retrofit retrofit = APIClient.getRetrofit();
        BreedsService service = retrofit.create(BreedsService.class);

        try {
            Response<BreedResponse> response = service.listBreeds().execute();
            BreedResponse breedResponse = response.body();
            breedsLiveData.postValue(
                breedResponse.getMessage()
                    .entrySet()
                    .stream()
                    .map(pair -> new Breed(pair.getKey(), pair.getValue()))
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortBreeds(Comparator<Breed> comparator) {
        List<Breed> b = breedsLiveData.getValue();
        if (b == null)
            throw new NullPointerException("Breeds is not set.");
        b.sort(comparator);
    }
}