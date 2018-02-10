package com.m.githubs.model;

/**
 * Created by kadan on 2/9/18.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Repo {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pushed_at")
    @Expose
    private String pushedAt;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("forks")
    @Expose
    private Integer forks;

   public Repo (String name, String pushedAt, int size, String language, int forks){
       this.name = name;
       this.pushedAt = pushedAt;
       this.size = size;
       this.language = language;
       this.forks = forks;
   }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPushedAt() {
        return pushedAt;
    }

    public void setPushedAt(String pushedAt) {
        this.pushedAt = pushedAt;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getForks() {
        return forks;
    }

    public void setForks(Integer forks) {
        this.forks = forks;
    }


}