package com.example.dragonsushi.DAO;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {
    private static final String API_URL = "https://192.168.0.18:45455/api";
    private static final String QUERY_PARAM = "param";
    private static final String PRINT_TYPE = "printType";

    //Esse método é responsável por pegar o Json
    public static String getJSONFromAPI(String url){
        String returning = "";
        try {
            URL apiEnd = new URL(url);
            int answerCod; /*codigoResposta*/
            HttpURLConnection connection;
            InputStream is;

            connection = (HttpURLConnection) apiEnd.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.connect();

            answerCod = connection.getResponseCode();
            if(answerCod < HttpURLConnection.HTTP_BAD_REQUEST){
                is = connection.getInputStream();
            }else{
                is = connection.getErrorStream();
            }

            returning = converterInputStreamToString(is);
            is.close();
            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return returning;
    }
    //Converte o InputStream que é uma classe de leitura, utilizada para ler o json em string
    private static String converterInputStreamToString(InputStream is){
        StringBuffer buffer = new StringBuffer();
        try{
            BufferedReader br;
            String line;

            br = new BufferedReader(new InputStreamReader(is));
            while((line = br.readLine())!=null){
                buffer.append(line);
            }

            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return buffer.toString();
    }
}
