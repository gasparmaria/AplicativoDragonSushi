package com.example.dragonsushi.Service;


import com.example.dragonsushi.Objects.Client;

import retrofit2.Call;
import retrofit.http.Body;
import retrofit.http.POST;

public interface UserClient {

    @POST("/Post")
    Call<Client> createAccount(@Body Client client);
}
