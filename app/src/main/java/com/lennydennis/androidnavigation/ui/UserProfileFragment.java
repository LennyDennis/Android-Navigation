package com.lennydennis.androidnavigation.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lennydennis.androidnavigation.R;
import com.lennydennis.androidnavigation.models.User;
import com.lennydennis.androidnavigation.viewmodel.SharedViewModel;

import org.w3c.dom.Text;

public class UserProfileFragment extends Fragment {
    private User mUser;
    private SharedViewModel mSharedViewModel;
    private ImageView userDisplayImage;
    private TextView userDisplayName;
    private  TextView userDisplayGender;
    private TextView userDisplayInterest;
    private TextView userDisplayStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userDisplayImage = view.findViewById(R.id.user_profile_image);
        userDisplayGender = view.findViewById(R.id.tv_profile_user_gender);
        userDisplayInterest = view.findViewById(R.id.tv_profile_user_interest);
        userDisplayName = view.findViewById(R.id.tv_profile_user_name);
        userDisplayStatus = view.findViewById(R.id.tv_profile_user_status);

        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getSelectedUser().observe(getViewLifecycleOwner(), user -> {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);
            Glide.with(getActivity())
                    .load(user.getProfileImage())
                    .apply(requestOptions)
                    .into(userDisplayImage);
            userDisplayName.setText(user.getName());
            userDisplayStatus.setText(user.getStatus());
            userDisplayInterest.setText(user.getInterestedIn());
            userDisplayGender.setText(user.getGender());
        });
    }
}