package com.example.breedsapplication.fragment.image.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.breedsapplication.payload.ImageResponse;
import com.example.breedsapplication.retrofit.APIClient;
import com.example.breedsapplication.retrofit.BreedsService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Response;
import retrofit2.Retrofit;

public class ImagesViewModel extends ViewModel {
    private final MutableLiveData<List<String>> imageList;

    public ImagesViewModel() {
        imageList = new MutableLiveData<>();
        imageList.setValue(null);
    }

    public LiveData<List<String>> getImageList() {
        return imageList;
    }

    public void loadImageList(String breed, String subBreed) {
        Retrofit retrofit = APIClient.getRetrofit();
        BreedsService service = retrofit.create(BreedsService.class);

        try {
            Response<ImageResponse> response = service.listBreedImages(breed).execute();
            ImageResponse imageResponse = response.body();

            if (imageResponse == null ||
                    !imageResponse.getStatus().equals("success")) {
                onLoadError((imageResponse == null) ? null : imageResponse.getStatus());
            }

            List<String> s;
            if (subBreed == null) {
                s = imageResponse.getMessage();
            } else {
                s = imageResponse.getMessage()
                        .stream()
                        .filter((String image) -> image
                                .split("/")[4]
                                .endsWith(subBreed))
                        .collect(Collectors.toList());
            }

            imageList.postValue(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onLoadError(String status) {
        throw new RuntimeException(
                String.format("Loading error. Status: \"%s\"", status));
    }
}