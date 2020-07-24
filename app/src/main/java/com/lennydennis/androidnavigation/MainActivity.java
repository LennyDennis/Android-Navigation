package com.lennydennis.androidnavigation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.lennydennis.androidnavigation.ui.HomeFragment;
import com.lennydennis.androidnavigation.util.PreferencesKey;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isFirstLogin();
        initializeFragment();
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