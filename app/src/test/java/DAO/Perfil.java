package DAO;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.dragonsushi.Objects.Pessoa;
import com.example.dragonsushi.Objects.Usuario;
import com.example.dragonsushi.R;

public class Perfil extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
        Usuario usuario = new Usuario();
        Pessoa pessoa = new Pessoa();
        String queryString;
        public TextView userName, login;

@Override
protected void onCreate(Bundle savedInstanceState){

super.onCreate(savedInstanceState);
setContentView(R.layout.activity_perfil);

userName = findViewById(R.id.txtNomeUsuario);
login = findViewById(R.id.txtEmailUsuario);

//Verificar a disponibilidade  da rede//
ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
NetworkInfo networkInfo = null;
    if (connMgr != null) {
        networkInfo = connMgr.getActiveNetworkInfo();
    }
    if (networkInfo != null && networkInfo.isConnected()) {
        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryString", queryString);
        getSupportLoaderManager().restartLoader(0, queryBundle, this);
    }

    public void getDataUser(View view) {
        // Recupera a string de busca.
        queryString = "/?name=" + nm_personagem.getText().toString();

        // esconde o teclado qdo o botão é clicado
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
}





    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
