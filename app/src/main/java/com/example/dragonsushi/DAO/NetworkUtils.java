package com.example.dragonsushi.DAO;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {
    private static Retrofit retrofit = null;

    public static UserDAO getApiInterface(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(UserDAO.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(UserDAO.class);
    }
}
