package com.example.breedsapplication.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Breed implements Serializable {
    private String name;
    private List<String> subBreeds;

    public Breed() {
    }

    public Breed(String name) {
        this(name, new ArrayList<>());
    }

    public Breed(String name, List<String> subBreeds) {
        this.name = name;
        this.subBreeds = subBreeds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSubBreeds() {
        return subBreeds;
    }

    public void setSubBreeds(List<String> subBreeds) {
        this.subBreeds = subBreeds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Breed breed = (Breed) o;
        return name.equals(breed.name) && Objects.equals(subBreeds, breed.subBreeds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, subBreeds);
    }

    @NonNull
    @Override
    public String toString() {
        return "Breed{" +
                "name='" + name + '\'' +
                '}';
    }

    public static class TitleComparator implements Comparator<Breed> {
        @Override
        public int compare(Breed b1, Breed b2) {
            return b1.getName().compareTo(b2.getName());
        }
    }

    public static class SubListSizeComparator implements Comparator<Breed> {
        @Override
        public int compare(Breed b1, Breed b2) {
            int s1 = b1.getSubBreeds().size();
            int s2 = b2.getSubBreeds().size();
            return Integer.compare(s1, s2);
        }
    }
}
