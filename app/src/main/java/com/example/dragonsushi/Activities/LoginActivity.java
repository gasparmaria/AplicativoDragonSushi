package com.example.dragonsushi.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.dragonsushi.DAO.LoadUser;
import com.example.dragonsushi.DAO.UserDAO;
import com.example.dragonsushi.Objects.User;
import com.example.dragonsushi.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String FILE_NAME = "usuarioLogado.json";
    EditText edtxtLogin, edtxtSenha;
    Button btnLogin;
    TextView txtCadastro;
    String login, senha;
    private String queryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtxtLogin = findViewById(R.id.edtxtLogin);
        edtxtSenha = findViewById(R.id.edtxtSenha);
        btnLogin = findViewById(R.id.btnLogin);
        txtCadastro = findViewById(R.id.txtCadastro);

        btnLogin.setOnClickListener(v -> {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (connMgr != null) {
                networkInfo = connMgr.getActiveNetworkInfo();
            }
            if (networkInfo != null && networkInfo.isConnected()) {
                Bundle queryBundle = new Bundle();
                queryBundle.putString("queryString", queryString);
                getSupportLoaderManager().restartLoader(0, queryBundle, this);
            }
        });
    }

    private void validarCampos() {
        login = String.valueOf(edtxtLogin.getText());
        senha = String.valueOf(edtxtSenha.getText());

        if (!campoNulo(login)) {
            edtxtLogin.requestFocus();
            Toast.makeText(this, "Preencha o campo e-mail.", Toast.LENGTH_SHORT).show();
        } else if (!campoNulo(senha)) {
            edtxtSenha.requestFocus();
            Toast.makeText(this, "Preencha o campo senha.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean campoNulo(String campo) {
        return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
    }

    public void getLogin(View view) {
        // Recupera a string de busca.
        queryString = "/?login=gerente";

        // esconde o teclado qdo o botão é clicado
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        //Verificar a disponibilidade  da rede//

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);

        } else {
            if (queryString.length() == 0) {
                edtxtLogin.setText("sem resultado");
                edtxtSenha.setText("sem resultado");
            } else {
                edtxtLogin.setText("sem internet");
                edtxtSenha.setText("sem internet");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        login = String.valueOf(edtxtLogin.getText());
        senha = String.valueOf(edtxtSenha.getText());

        outState.putString("login", login);
        outState.putString("senha", senha);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "gerente";
        if (args != null) {
            queryString = args.getString("queryString");
        }
        return new LoadUser(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            // Converte a resposta em Json
            JSONObject jsonObject = new JSONObject(data);

            // inicializa o contador
            String login = null;
            String senha = null;

            JSONObject user = jsonObject.getJSONObject("result");

            try{
                login = user.getString("login");
                senha = user.getString("senha");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            User userin = new User();
            userin.setLogin(user.getString("login"));
            userin.setSenha(user.getString("senha"));

            edtxtLogin.setText(userin.getLogin());
            edtxtSenha.setText(userin.getSenha());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
