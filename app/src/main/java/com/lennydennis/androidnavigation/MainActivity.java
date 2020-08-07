package com.lennydennis.androidnavigation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.lennydennis.androidnavigation.ui.AgreementFragment;
import com.lennydennis.androidnavigation.ui.HomeFragment;
import com.lennydennis.androidnavigation.ui.MessagesFragment;
import com.lennydennis.androidnavigation.ui.SavedConnectionFragment;
import com.lennydennis.androidnavigation.ui.SettingsFragment;
import com.lennydennis.androidnavigation.util.PreferencesKey;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView mBottomNavigationView;
    private ImageView mHeaderImageView;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerview = navigationView.getHeaderView(0);
        mHeaderImageView = headerview.findViewById(R.id.header_image);

        isFirstLogin();
        initializeFragment();
        setNavigationViewListener();
        setHeaderImage();

    }

    private void setHeaderImage() {
        Glide.with(this)
                .load(R.drawable.couple)
                .into(mHeaderImageView);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home: {
                initializeFragment();
                break;
            }
            case R.id.settings: {
                SettingsFragment settingsFragment = new SettingsFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content_frame, settingsFragment, getString(R.string.tag_fragment_home));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                item.setChecked(true);
                break;
            }
            case R.id.agreement: {
                AgreementFragment agreementFragment = new AgreementFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content_frame, agreementFragment, getString(R.string.tag_fragment_home));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            }
            case R.id.bottom_nav_home: {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content_frame, homeFragment, getString(R.string.tag_fragment_home));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                item.setChecked(true);
                break;
            }
            case R.id.bottom_nav_favorite: {
                SavedConnectionFragment savedConnectionFragment = new SavedConnectionFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content_frame, savedConnectionFragment, getString(R.string.tag_fragment_saved_connections));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                item.setChecked(true);
                break;
            }
            case R.id.bottom_nav_messages: {
                MessagesFragment messagesFragment = new MessagesFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content_frame, messagesFragment, getString(R.string.tag_fragment_messages));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                item.setChecked(true);
                break;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    private void initializeFragment() {
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content_frame, homeFragment, getString(R.string.tag_fragment_home));
        fragmentTransaction.addToBackStack(getString(R.string.home));
        fragmentTransaction.commit();
    }

    private void setNavigationViewListener(){
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void isFirstLogin() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstLogin = sharedPreferences.getBoolean(PreferencesKey.FIRST_TIME_LOGIN, true);

        if (isFirstLogin) {

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