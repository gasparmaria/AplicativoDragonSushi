package com.example.dragonsushi.DAO;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.JsonReader;
import android.util.Log;

import com.example.dragonsushi.Objects.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserDAO {
    NetworkUtils networkUtils;

    private static final String API_URL = "https://192.168.0.18:45455/api";

    @SuppressLint("RestrictedApi")
    static String getLogin(String queryString){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String userJSONString = null;

        try {
            Uri builtURI;
            if(queryString == null){
                builtURI = Uri.parse(API_URL).buildUpon().build();
            }
            else {
                String url1 = API_URL + "/UsuarioApi/ConsultarUsuario/?login=" + queryString;
                builtURI = Uri.parse(url1).buildUpon().build();
            }
            URL requestURL = new URL(builtURI.toString());

            // Abre a conexão de rede
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String linha;

            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
                builder.append("\n");
            }

            if (builder.length() == 0) {
                return null;
            }

            userJSONString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, userJSONString);
        return userJSONString;
    }

    static void postUser(User user){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String characterJSONString = null;

        try {
            Uri builtURI;
            if(user == null){
                builtURI = Uri.parse(API_URL).buildUpon().build();
            }
            else {
                String url1 = API_URL + "/UsuarioApi/CadastrarUsuario/";
                builtURI = Uri.parse(url1).buildUpon().build();
            }

            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

            JsonReader jsonReader = new JsonReader(inputStreamReader);
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String url = jsonReader.nextName();
                if (url.equals(API_URL)) {
                    Gson gson = new Gson();
                    String userAdd = gson.toJson(user);
                    userAdd = jsonReader.nextString();
                    break;
                } else {
                    jsonReader.skipValue(); // Skip values of other keys
                }
            }
            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
