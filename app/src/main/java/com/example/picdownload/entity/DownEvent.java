package com.example.picdownload.entity;

/**
 * Created by xlzhen on 1/24 0024.
 */
public class DownEvent{
    private int loading;
    private Type type;
    private long taskId;
    private String imageUrl;

    public DownEvent(int loading, Type type, long taskId, String imageUrl) {
        this.loading = loading;
        this.type = type;
        this.taskId = taskId;
        this.imageUrl = imageUrl;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getLoading() {
        return loading;
    }

    public void setLoading(int loading) {
        this.loading = loading;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type{
        loading,complete,error
    }
}
