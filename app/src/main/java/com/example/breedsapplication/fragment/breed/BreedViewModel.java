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
     * Calls the API and gets the breeds list.
     */
    public void loadBreeds() {
        Retrofit retrofit = APIClient.getRetrofit();
        BreedsService service = retrofit.create(BreedsService.class);

        try {
            Response<BreedResponse> response = service.listBreeds().execute();
            BreedResponse breedResponse = response.body();

            if (breedResponse == null ||
                    !breedResponse.getStatus().equals("success")) {
                onLoadError((breedResponse == null) ? null : breedResponse.getStatus());
            }

            breedsLiveData.postValue(
                    breedResponse.getMessage()
                            .entrySet()
                            .stream()
                            .map(pair -> new Breed(pair.getKey(), pair.getValue()))
                            // TODO: remove sort on release ver
                            .sorted(new Breed.SubListSizeComparator().reversed())
                            .collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onLoadError(String status) {
        // TODO: display error in dialog
        throw new RuntimeException(
                String.format("Loading error. Status: \"%s\"", status));
    }

    public void sortBreeds(Comparator<Breed> comparator) {
        List<Breed> b = breedsLiveData.getValue();
        if (b == null)
            throw new NullPointerException("Breeds is not set.");
        b.sort(comparator);
    }
}