package com.example.breedsapplication.payload;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BreedResponse implements Serializable {
    private Map<String, List<String>> message;
    private String status;

    public BreedResponse() {
    }

    public BreedResponse(Map<String, List<String>> message, String status) {
        this.message = message;
        this.status = status;
    }

    public Map<String, List<String>> getMessage() {
        return message;
    }

    public void setMessage(Map<String, List<String>> message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BreedResponse that = (BreedResponse) o;
        return Objects.equals(message, that.message) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, status);
    }

    @Override
    public String toString() {
        return "BreedResponse{" +
                "message=" + message +
                ", status='" + status + '\'' +
                '}';
    }
}
