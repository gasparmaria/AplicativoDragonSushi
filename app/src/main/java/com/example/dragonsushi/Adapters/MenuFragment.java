package com.example.dragonsushi.Adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.dragonsushi.Activities.BuscaActivity;
import com.example.dragonsushi.Activities.HomeActivity;
import com.example.dragonsushi.R;

public class MenuFragment extends Fragment {

    public MenuFragment() {

    }

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inicia o layoute para o fragmento
        final View rootView = inflater.inflate(R.layout.bottom_menu,
                container, false);
        final ImageButton btnHome = rootView.findViewById(R.id.menu_home);
        final ImageButton btnCart = rootView.findViewById(R.id.menu_cart);
        final ImageButton btnSearch = rootView.findViewById(R.id.menu_search);
        final ImageButton btnPerson = rootView.findViewById(R.id.menu_person);

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HomeActivity.class);
            startActivity(intent);
        });
        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HomeActivity.class);
            startActivity(intent);
        });
        btnSearch.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), BuscaActivity.class);
            startActivity(intent);
        });
        btnPerson.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HomeActivity.class);
            startActivity(intent);
        });

        return rootView;
    }
}
