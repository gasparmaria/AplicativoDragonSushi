package com.example.dragonsushi.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dragonsushi.DataBase;
import com.example.dragonsushi.Objects.Client;
import com.example.dragonsushi.Objects.Person;
import com.example.dragonsushi.Objects.User;

public class UserDAO {
    private final DataBase dataBase;
    private final SQLiteDatabase db;

    public UserDAO(Context context){
        dataBase = new DataBase(context);
        db = dataBase.getWritableDatabase();
    }

    public Client selectClient(String email, String password){
        Person person = new Person();
        User user = new User();
        Cursor cursor = db.query("tbClient",
                new String[]{
                        "idUser," +
                        "idPerson," +
                        "name," +
                        "email," +
                        "password"
                },
                "email = ? AND password = ?",
                new String[]{email, password},
                null,
                null,
                null,
                String.valueOf(1));

        while(cursor.moveToNext()){
            user.setIdUsuario(cursor.getInt(0));
            person.setId(cursor.getInt(1));
            person.setNome(cursor.getString(2));
            user.setLogin(cursor.getString(3));
            user.setSenha(cursor.getString(4));
        }

        Client client = new Client(user, person);
        return client;
    }
}

