package com.example.dragonsushi.DAO;

import com.example.dragonsushi.Objects.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserDAO {
    String BASE_URL = "";

    @GET("api/UsuarioApi/ConsultarUsuario")
    Call<User> getAllData(
            @Query("login") String login
    );
}
