package com.lennydennis.androidnavigation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;
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

public class MainActivity extends AppCompatActivity implements IMainActivity, BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

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

    private static final int HOME_FRAGMENT = 0;
    private static final int CONNECTIONS_FRAGMENT = 1;
    private static final int MESSAGES_FRAGMENT = 2;

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

     public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home: {
                mFragmentTags.clear();
                mFragmentTags = new ArrayList<>();
                initializeFragment();
                break;
            }
            case R.id.settings: {
                if (mSettingsFragment  == null) {
                    mSettingsFragment = new SettingsFragment();
                    fragmentTransaction(mSettingsFragment,  getString(R.string.tag_fragment_settings));
                } else {
                    mFragmentTags.remove( getString(R.string.tag_fragment_settings));
                    mFragmentTags.add( getString(R.string.tag_fragment_settings));
                }
                item.setChecked(true);
                setFragmentVisibilities( getString(R.string.tag_fragment_settings));
                break;
            }
            case R.id.agreement: {
                if (mAgreementFragment  == null) {
                    mAgreementFragment = new AgreementFragment();
                    fragmentTransaction(mAgreementFragment, getString(R.string.tag_fragment_agreement));
                } else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_agreement));
                    mFragmentTags.add(getString(R.string.tag_fragment_agreement));
                }
                setFragmentVisibilities(getString(R.string.tag_fragment_home));
                break;
            }
            case R.id.bottom_nav_home: {
                if (mHomeFragment  == null) {
                    mHomeFragment = new HomeFragment();
                    fragmentTransaction(mHomeFragment, getString(R.string.tag_fragment_home));
                } else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_home));
                    mFragmentTags.add(getString(R.string.tag_fragment_home));
                }
                item.setChecked(true);
                setFragmentVisibilities(getString(R.string.tag_fragment_home));
                break;
            }
            case R.id.bottom_nav_favorite: {
                if (mSavedConnectionFragment  == null) {
                    mSavedConnectionFragment = new SavedConnectionFragment();
                    fragmentTransaction(mSavedConnectionFragment, getString(R.string.tag_fragment_saved_connections));
                } else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_saved_connections));
                    mFragmentTags.add(getString(R.string.tag_fragment_saved_connections));
                }
                item.setChecked(true);
                setFragmentVisibilities(getString(R.string.tag_fragment_saved_connections));
                break;
            }
            case R.id.bottom_nav_messages: {
                if (mMessagesFragment  == null) {
                    mMessagesFragment = new MessagesFragment();
                    fragmentTransaction(mMessagesFragment, getString(R.string.tag_fragment_messages));
                } else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_messages));
                    mFragmentTags.add(getString(R.string.tag_fragment_messages));
                }
                item.setChecked(true);
                setFragmentVisibilities(getString(R.string.tag_fragment_messages));
                break;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    private void initializeFragment() {
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
            fragmentTransaction(mHomeFragment, getString(R.string.tag_fragment_home));
        } else {
            mFragmentTags.remove(getString(R.string.tag_fragment_home));
            mFragmentTags.add(getString(R.string.tag_fragment_home));
        }
        setFragmentVisibilities(getString(R.string.tag_fragment_home));
    }

    @Override
    public void onBackPressed() {
        int backStackCount = mFragmentTags.size();
        if(backStackCount > 1){
            String topFragmentTag = mFragmentTags.get(backStackCount - 1);
            String newTopFragmentTag = mFragmentTags.get(backStackCount - 2);

            setFragmentVisibilities(newTopFragmentTag);
            mFragmentTags.remove(topFragmentTag);

            mExitCount = 0;
        }else if(backStackCount == 1){

            String topFragmentTag = mFragmentTags.get(backStackCount - 1);

            if(topFragmentTag.equals(getString(R.string.tag_fragment_home))){
                mHomeFragment.scrollToTop();
                mExitCount++;
                Toast.makeText(this, "1 more click to exit", Toast.LENGTH_SHORT).show();
            }
            else{
                mExitCount++;
                Toast.makeText(this, "1 more click to exit", Toast.LENGTH_SHORT).show();
            }
        }

        if(mExitCount >= 2){
            super.onBackPressed();
        }
    }

    private void setNavigationIcon(String tagname) {
        Menu menu = mBottomNavigationView.getMenu();
        MenuItem menuItem = null;
        if (tagname.equals(getString(R.string.tag_fragment_home))) {
            menuItem = menu.getItem(HOME_FRAGMENT);
            menuItem.setChecked(true);
        }
        else if (tagname.equals(getString(R.string.tag_fragment_saved_connections))) {
            menuItem = menu.getItem(CONNECTIONS_FRAGMENT);
            menuItem.setChecked(true);
        }
        else if (tagname.equals(getString(R.string.tag_fragment_messages))) {
            menuItem = menu.getItem(MESSAGES_FRAGMENT);
            menuItem.setChecked(true);
        }
    }

    private void hideBottomNavigation() {
        if (mBottomNavigationView != null) {
            mBottomNavigationView.setVisibility(View.GONE);
        }
    }

    private void showBottomNavigation() {
        if (mBottomNavigationView != null) {
            mBottomNavigationView .setVisibility(View.VISIBLE);
        }
    }

    private void setFragmentVisibilities(String tagname){
        if(tagname.equals(getString(R.string.tag_fragment_home)))
            showBottomNavigation();
        else if(tagname.equals(getString(R.string.tag_fragment_saved_connections)))
            showBottomNavigation();
        else if(tagname.equals(getString(R.string.tag_fragment_messages)))
            showBottomNavigation();
        else if(tagname.equals(getString(R.string.tag_fragment_settings)))
            hideBottomNavigation();
        else if(tagname.equals(getString(R.string.tag_fragment_view_profile)))
            hideBottomNavigation();
        else if(tagname.equals(getString(R.string.tag_fragment_chat)))
            hideBottomNavigation();
        else if(tagname.equals(getString(R.string.tag_fragment_agreement)))
            hideBottomNavigation();

        for(int i = 0; i < mFragments.size(); i++){
            if(tagname.equals(mFragments.get(i).getTag())){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.show((mFragments.get(i).getFragment()));
                transaction.commit();
            }
            else{
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide((mFragments.get(i).getFragment()));
                transaction.commit();
            }
        }
        setNavigationIcon(tagname);
    }

    public void fragmentTransaction(Fragment fragment, String fragmentTag) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.main_content_frame, fragment, fragmentTag);
            mFragmentTags.add(fragmentTag);
            mFragments.add(new FragmentTag(fragment, fragmentTag));
            fragmentTransaction.commit();

    }

    private void setNavigationViewListener() {
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