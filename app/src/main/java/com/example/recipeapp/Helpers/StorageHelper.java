package com.example.recipeapp.Helpers;

import com.example.recipeapp.Models.UserEntity;

public class StorageHelper {

    private static StorageHelper instance;
    private UserEntity userEntity;

    public static  StorageHelper getInstance(){
        if(instance ==null){
            instance = new StorageHelper();
        }
        return instance;
    }
    public UserEntity getUserEntity() {
        return userEntity;
    }
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}