package com.example.breedsapplication.retrofit;

import com.example.breedsapplication.payload.BreedResponse;
import com.example.breedsapplication.payload.ImageResponse;
import com.example.breedsapplication.payload.SubBreedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BreedsService {
    @GET("breeds/list/all")
    Call<BreedResponse> listBreeds();

    @GET("breed/list/{breed}/list")
    Call<SubBreedResponse> listSubBreeds(@Path("breed") String breed);

    @GET("breed/list/{breed}/images")
    Call<ImageResponse> listBreedImages(@Path("breed") String breed);
}
