package com.lennydennis.androidnavigation.ui;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lennydennis.androidnavigation.R;
import com.lennydennis.androidnavigation.models.User;
import com.lennydennis.androidnavigation.util.PreferencesKey;
import com.lennydennis.androidnavigation.util.Resources;
import com.lennydennis.androidnavigation.viewmodel.SharedViewModel;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UserProfileFragment extends Fragment implements OnLikeListener {
    private ImageView userDisplayImage, mBackArrow;
    private TextView userDisplayName, userDisplayGender, userDisplayInterest, userDisplayStatus, userToolbarName;
    private LikeButton mLikeButton;

    private User mUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        userDisplayImage = view.findViewById(R.id.user_profile_image);
        userDisplayGender = view.findViewById(R.id.tv_profile_user_gender);
        userDisplayInterest = view.findViewById(R.id.tv_profile_user_interest);
        userDisplayName = view.findViewById(R.id.tv_profile_user_name);
        userDisplayStatus = view.findViewById(R.id.tv_profile_user_status);
        mBackArrow = view.findViewById(R.id.back_arrow);
        userToolbarName = view.findViewById(R.id.user_profile_heading);
        mLikeButton = view.findViewById(R.id.heart_button);

        mLikeButton.setOnLikeListener(this);
        setBackgroundImage(view);

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getSelectedUser().observe(getViewLifecycleOwner(), user -> {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);
            mUser = user;
            if (mUser != null) {
                checkIfConnected();
                Glide.with(requireActivity())
                        .load(user.getProfileImage())
                        .circleCrop()
                        .apply(requestOptions)
                        .into(userDisplayImage);
                userDisplayName.setText(user.getName());
                userDisplayStatus.setText(user.getStatus());
                userDisplayInterest.setText(user.getInterestedIn());
                userDisplayGender.setText(user.getGender());
                userToolbarName.setText(user.getName()+" Profile");
            }
        });
    }

    private void setBackgroundImage(View view) {
        ImageView backgroundView = view.findViewById(R.id.background);
        Glide.with(requireActivity())
                .load(Resources.BACKGROUND_HEARTS)
                .into(backgroundView);
    }

    private void checkIfConnected(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        Set<String> savedNames = preferences.getStringSet(PreferencesKey.SAVED_CONNECTIONS, new HashSet<String>());
        if(savedNames.contains(mUser.getName())){
            Log.d(TAG, "checkIfConnected: liked.");
            mLikeButton.setLiked(true);
        }
        else{
            Log.d(TAG, "checkIfConnected: not liked.");
            mLikeButton.setLiked(false);
        }
    }

    @Override
    public void liked(LikeButton likeButton) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> savedNames = sharedPreferences.getStringSet(PreferencesKey.SAVED_CONNECTIONS, new HashSet<String>());
        savedNames.add(mUser.getName());
        editor.putStringSet(PreferencesKey.SAVED_CONNECTIONS, savedNames);
        editor.apply();
    }

    @Override
    public void unLiked(LikeButton likeButton) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> savedNames = sharedPreferences.getStringSet(PreferencesKey.SAVED_CONNECTIONS, new HashSet<String>());
        savedNames.remove(mUser.getName());
        editor.apply();
        editor.putStringSet(PreferencesKey.SAVED_CONNECTIONS, savedNames);
        editor.apply();
    }
}