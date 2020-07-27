package com.lennydennis.androidnavigation.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lennydennis.androidnavigation.R;
import com.lennydennis.androidnavigation.models.User;
import com.lennydennis.androidnavigation.viewmodel.SharedViewModel;

public class UserProfileFragment extends Fragment {
    private User mUser;
    private SharedViewModel mSharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        mSharedViewModel.getSelectedUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                mUser = user;
            }
        });
    }
}