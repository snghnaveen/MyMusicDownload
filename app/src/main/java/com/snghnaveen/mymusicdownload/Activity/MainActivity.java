package com.snghnaveen.mymusicdownload.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.snghnaveen.mymusicdownload.Fragments.Home;
import com.snghnaveen.mymusicdownload.Fragments.SearchResult;
import com.snghnaveen.mymusicdownload.Helper.AppPreference;
import com.snghnaveen.mymusicdownload.Models.ApiResponses.XYZResponse;
import com.snghnaveen.mymusicdownload.R;
import com.snghnaveen.mymusicdownload.Services.ServiceInterface;

public class MainActivity extends AppCompatActivity implements ServiceInterface {

    public static AppPreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appPreference = new AppPreference(this);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new Home())
                .commit();
    }

    @Override
    public void searchSong(XYZResponse resp) {


        appPreference.setXYZResponse(resp);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new SearchResult())
                .addToBackStack(null)
                .commit();
    }

}
