package com.example.dragonsushi.DAO;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String API_URL = "https://192.168.0.18:45455/api";
    private static final String QUERY_PARAM = "param";
    private static final String PRINT_TYPE = "printType";

    static String getData(String queryString){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String userJSONString = null;

        try {
            Uri builtURI;
            if(queryString == null){
                builtURI = Uri.parse(API_URL).buildUpon().build();
            }
            else {
                String url1 = API_URL + queryString;
                //Construção da URI de Busca
                builtURI = Uri.parse(url1).buildUpon().build();
            }
            // Converte a URI para a URL.
            URL requestURL = new URL(builtURI.toString());
            // Abre a conexão de rede
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Busca o InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            // Cria o buffer para o input stream
            reader = new BufferedReader(new InputStreamReader(inputStream));
            // Usa o StringBuilder para receber a resposta.
            StringBuilder builder = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Adiciona a linha a string.
                builder.append(linha);
                builder.append("\n");
            }
            if (builder.length() == 0) {
                // se o stream estiver vazio não faz nada
                return null;
            }
            userJSONString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // fecha a conexão e o buffer.
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
        // escreve o Json no log
        Log.d(LOG_TAG, userJSONString);
        return userJSONString;
    }

}
