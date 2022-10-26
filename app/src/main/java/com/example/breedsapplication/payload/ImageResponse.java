package com.example.breedsapplication.payload;

import java.util.List;
import java.util.Objects;

public class ImageResponse {
    private List<String> message;
    private String status;

    public ImageResponse() {
    }

    public ImageResponse(List<String> message, String status) {
        this.message = message;
        this.status = status;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
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
        ImageResponse that = (ImageResponse) o;
        return Objects.equals(message, that.message) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, status);
    }

    @Override
    public String toString() {
        return "ImageResponse{" +
                "message=" + message +
                ", status='" + status + '\'' +
                '}';
    }
}
