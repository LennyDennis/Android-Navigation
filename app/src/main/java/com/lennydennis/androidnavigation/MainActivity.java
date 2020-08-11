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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.lennydennis.androidnavigation.models.FragmentTag;
import com.lennydennis.androidnavigation.ui.AgreementFragment;
import com.lennydennis.androidnavigation.ui.HomeFragment;
import com.lennydennis.androidnavigation.ui.MessagesFragment;
import com.lennydennis.androidnavigation.ui.SavedConnectionFragment;
import com.lennydennis.androidnavigation.ui.SettingsFragment;
import com.lennydennis.androidnavigation.util.PreferencesKey;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView mBottomNavigationView;
    private ImageView mHeaderImageView;
    private DrawerLayout mDrawerLayout;

    private SettingsFragment mSettingsFragment;
    private AgreementFragment mAgreementFragment;
    private HomeFragment mHomeFragment;
    private SavedConnectionFragment mSavedConnectionFragment;
    private MessagesFragment mMessagesFragment;

    public ArrayList<String> mFragmentTags = new ArrayList<>();
    public ArrayList<FragmentTag> mFragments = new ArrayList<>();
    private int mExitCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        mHeaderImageView = headerView.findViewById(R.id.header_image);

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
                mSettingsFragment = new SettingsFragment();
                fragmentTransaction(mSettingsFragment,getString(R.string.tag_fragment_home));
                item.setChecked(true);
                break;
            }
            case R.id.agreement: {
                mAgreementFragment = new AgreementFragment();
                fragmentTransaction(mAgreementFragment,getString(R.string.tag_fragment_home));
                break;
            }
            case R.id.bottom_nav_home: {
                mHomeFragment = new HomeFragment();
                fragmentTransaction(mHomeFragment,getString(R.string.tag_fragment_home));
                item.setChecked(true);
                break;
            }
            case R.id.bottom_nav_favorite: {
                mSavedConnectionFragment = new SavedConnectionFragment();
                fragmentTransaction(mSavedConnectionFragment,getString(R.string.tag_fragment_saved_connections));
                item.setChecked(true);
                break;
            }
            case R.id.bottom_nav_messages: {

                mMessagesFragment = new MessagesFragment();
                fragmentTransaction(mMessagesFragment,getString(R.string.tag_fragment_messages));
                item.setChecked(true);
                break;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    private void initializeFragment() {
        mHomeFragment = new HomeFragment();
        fragmentTransaction(mHomeFragment,getString(R.string.tag_fragment_home));
    }

    public void fragmentTransaction(Fragment fragment, String fragmentTag){
        if(fragment == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_content_frame, fragment, fragmentTag);
            mFragmentTags.add(fragmentTag);
            mFragments.add(new FragmentTag(fragment, fragmentTag));
            fragmentTransaction.commit();
        }else{
            mFragmentTags.remove(fragmentTag);
            mFragmentTags.add(fragmentTag);
        }
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