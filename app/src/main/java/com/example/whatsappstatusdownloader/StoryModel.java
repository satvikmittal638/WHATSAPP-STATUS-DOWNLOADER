package com.example.whatsappstatusdownloader;

import android.net.Uri;

public class StoryModel {
    private String name,path,filename;
    private Uri fileUri;

    public StoryModel() {
    }
    public StoryModel(String name, String path, String filename, Uri fileUri) {
        this.name = name;
        this.path = path;
        this.filename = filename;
        this.fileUri = fileUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }
}
