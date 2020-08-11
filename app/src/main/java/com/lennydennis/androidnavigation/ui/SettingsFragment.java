package com.lennydennis.androidnavigation.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lennydennis.androidnavigation.IMainActivity;
import com.lennydennis.androidnavigation.R;
import com.lennydennis.androidnavigation.util.PreferencesKey;
import com.lennydennis.androidnavigation.util.Resources;

import java.util.Objects;

public class SettingsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "SettingsFragment";
    private static final int NEW_PHOTO_REQUEST = 3567;
    private static final int VERIFY_PERMISSIONS_REQUEST = 1235;

    private IMainActivity mInterface;

    private ImageView mBackArrow;
    private TextView mFragmentHeading;
    private EditText mName;
    private Spinner mGenderSpinner, mInterestedInSpinner, mStatusSpinner;
    private ImageView mProfileImage;
    private Button mSave;

    private String mSelectedGender, mSelectedInterest, mSelectedStatus, mSelectedImageUrl;
    private Boolean mPermissionsChecked = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mBackArrow = view.findViewById(R.id.back_arrow_settings);
        mFragmentHeading = view.findViewById(R.id.settings_heading);
        mName = view.findViewById(R.id.et_name_settings);
        mGenderSpinner = view.findViewById(R.id.gender_spinner);
        mInterestedInSpinner = view.findViewById(R.id.interested_in_spinner);
        mStatusSpinner = view.findViewById(R.id.relationship_status_spinner);
        mProfileImage = view.findViewById(R.id.profile_image);
        mSave = view.findViewById(R.id.btn_save);

        mBackArrow.setOnClickListener(this);
        mProfileImage.setOnClickListener(this);
        mSave.setOnClickListener(this);

        setBackgroundImage(view);
        initToolbar();
        getSavedPreferences();

        return view;
    }

    private void getSavedPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String name = preferences.getString(PreferencesKey.NAME, "");
        mName.setText(name);

        mSelectedGender = preferences.getString(PreferencesKey.GENDER, getString(R.string.gender_none));
        String[] genderArray = getActivity().getResources().getStringArray(R.array.gender_array);
        for(int i = 0; i < genderArray.length; i++){
            if(genderArray[i].equals(mSelectedGender)){
                mGenderSpinner.setSelection(i, false);
            }
        }

        mSelectedInterest = preferences.getString(PreferencesKey.INTERESTED_IN, getString(R.string.interested_in_anyone));
        String[] interestArray = getActivity().getResources().getStringArray(R.array.interested_in_array);
        for(int i = 0; i < interestArray.length; i++){
            if(interestArray[i].equals(mSelectedInterest)){
                mInterestedInSpinner.setSelection(i, false);
            }
        }

        mSelectedStatus = preferences.getString(PreferencesKey.RELATIONSHIP_STATUS, getString(R.string.status_looking));
        String[] statusArray = getActivity().getResources().getStringArray(R.array.relationship_status_array);
        for(int i = 0; i < statusArray.length; i++){
            if(statusArray[i].equals(mSelectedStatus)){
                mStatusSpinner.setSelection(i, false);
            }
        }

        mSelectedImageUrl = preferences.getString(PreferencesKey.PROFILE_IMAGE, "");
        if(!mSelectedImageUrl.equals("")){
            Glide.with(this)
                    .load(mSelectedImageUrl)
                    .into(mProfileImage);
        }

        mGenderSpinner.setOnItemSelectedListener(this);
        mInterestedInSpinner.setOnItemSelectedListener(this);
        mStatusSpinner.setOnItemSelectedListener(this);

        Log.d(TAG, "getSavedPreferences: name: " + name);
        Log.d(TAG, "getSavedPreferences: gender: " + mSelectedGender);
        Log.d(TAG, "getSavedPreferences: interested in: " + mSelectedInterest);
        Log.d(TAG, "getSavedPreferences: status: " + mSelectedStatus);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_arrow_settings) {
            mInterface.onBackPressed();
        }
        if(v.getId() == R.id.btn_save){
            savePreferences();
        }

        if(v.getId() == R.id.profile_image){

        }
    }

    private void initToolbar(){
        Log.d(TAG, "initToolbar: initializing toolbar.");
        mBackArrow.setOnClickListener(this);
        mFragmentHeading.setText(getString(R.string.tag_fragment_settings));

    }

    private void savePreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();

        String name = mName.getText().toString();
        if(!name.equals("")){
            editor.putString(PreferencesKey.NAME, name);
            editor.apply();
        }
        else{
            Toast.makeText(getActivity(), "Enter your name", Toast.LENGTH_SHORT).show();
        }

        editor.putString(PreferencesKey.GENDER, mSelectedGender);
        editor.apply();

        editor.putString(PreferencesKey.INTERESTED_IN, mSelectedInterest);
        editor.apply();

        editor.putString(PreferencesKey.RELATIONSHIP_STATUS, mSelectedStatus);
        editor.apply();

        if(!mSelectedImageUrl.equals("")){
            editor.putString(PreferencesKey.PROFILE_IMAGE, mSelectedImageUrl);
            editor.apply();
        }

        Toast.makeText(getActivity(), "saved", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "savePreferences: name: " + name);
        Log.d(TAG, "savePreferences: gender: " + mSelectedGender);
        Log.d(TAG, "savePreferences: interested in: " + mSelectedInterest);
        Log.d(TAG, "savePreferences: status: " + mSelectedStatus);
    }

    private void setBackgroundImage(View view){
        ImageView backgroundView = view.findViewById(R.id.background);
        Glide.with(requireActivity())
                .load(Resources.BACKGROUND_HEARTS)
                .into(backgroundView);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInterface = (IMainActivity) getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called.");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        Log.d(TAG, "onItemSelected: clicked.");

        switch (adapterView.getId()){

            case R.id.gender_spinner:
                Log.d(TAG, "onItemSelected: selected a gender: " + (String)adapterView.getItemAtPosition(pos));
                mSelectedGender = (String)adapterView.getItemAtPosition(pos);
                break;

            case R.id.interested_in_spinner:
                Log.d(TAG, "onItemSelected: selected an interest: " + (String)adapterView.getItemAtPosition(pos));
                mSelectedInterest = (String)adapterView.getItemAtPosition(pos);
                break;

            case R.id.relationship_status_spinner:
                Log.d(TAG, "onItemSelected: selected a status: " + (String)adapterView.getItemAtPosition(pos));
                mSelectedStatus = (String)adapterView.getItemAtPosition(pos);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d(TAG, "onNothingSelected: nothing is selected.");
    }
}