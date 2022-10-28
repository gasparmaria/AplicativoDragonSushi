package com.example.dragonsushi.DAO;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class LoadUser extends AsyncTaskLoader<String> {

    private String mQueryString;
    LoadUser(Context context, String queryString) {
        super(context);
        mQueryString = queryString;

    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public String loadInBackground() {

        return NetworkUtils.getData(mQueryString);
    }

}
