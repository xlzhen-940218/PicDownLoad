package com.example.picdownload.entity;

/**
 * Created by xlzhen on 1/24 0024.
 */
public class ImageEntity {
    private String imageUrl;
    private boolean isCheck;

    public ImageEntity(String imageUrl, boolean isCheck) {
        this.imageUrl = imageUrl;
        this.isCheck = isCheck;
    }

    public ImageEntity() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}
