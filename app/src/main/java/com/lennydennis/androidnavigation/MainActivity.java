package com.lennydennis.androidnavigation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lennydennis.androidnavigation.ui.HomeFragment;
import com.lennydennis.androidnavigation.ui.MessagesFragment;
import com.lennydennis.androidnavigation.ui.SavedConnectionFragment;
import com.lennydennis.androidnavigation.util.PreferencesKey;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        isFirstLogin();
        initializeFragment();

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.bottom_nav_home:{
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content_frame, homeFragment, getString(R.string.tag_fragment_home));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                item.setChecked(true);
                break;
            }
            case R.id.bottom_nav_favorite:{
                SavedConnectionFragment savedConnectionFragment = new SavedConnectionFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content_frame, savedConnectionFragment, getString(R.string.tag_fragment_saved_connections));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                item.setChecked(true);
                break;
            }
            case R.id.bottom_nav_messages:{
                MessagesFragment messagesFragment = new MessagesFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content_frame,messagesFragment,getString(R.string.tag_fragment_messages));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                item.setChecked(true);
                break;
            }
        }
        return false;

    }

    private void initializeFragment(){
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content_frame, homeFragment, getString(R.string.tag_fragment_home));
        fragmentTransaction.addToBackStack(getString(R.string.home));
        fragmentTransaction.commit();
    }

    private void isFirstLogin(){
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstLogin = sharedPreferences.getBoolean(PreferencesKey.FIRST_TIME_LOGIN, true);

        if(isFirstLogin){

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(R.string.welcome_message);
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(PreferencesKey.FIRST_TIME_LOGIN, false);
                    editor.apply();
                    dialog.dismiss();
                }
            });
            alertDialogBuilder.setIcon(R.drawable.ic_baseline_favorite_24);
            alertDialogBuilder.setTitle(" ");
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }


}